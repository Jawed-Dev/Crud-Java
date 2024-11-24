package crud.dao;
import crud.database.DatabaseConnection;
import crud.entity.EntityUser;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoUser {
    private final DatabaseConnection databaseConnection;

    public DaoUser(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public boolean addUser(EntityUser entityUser) {
        String query = "INSERT INTO user (user_first_name, user_last_name, user_email, user_create_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entityUser.getFirstName());
            preparedStatement.setString(2, entityUser.getLastName());
            preparedStatement.setString(3, entityUser.getEmail());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(4, timestamp);

            int affectedRows = preparedStatement.executeUpdate();

            // set userID in Model
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) throw new SQLException("ID user non obtenu.");
                entityUser.updateId(generatedKeys.getInt(1));
            }
            return affectedRows > 0;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return false;
        }
    }

    public boolean deleteUser(EntityUser entityUser) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entityUser.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return false;
        }
    }

    public List<EntityUser> getUsersBySearch(SearchUserByParams searchUserByParams) {
        /*
         * This method uses Builder pattern to create a dynamic SQL,
         * Depending on which parameters are provided, additional WHERE conditions are appended to the query.
         */
        StringBuilder query = new StringBuilder(
                "SELECT user_id, user_first_name, user_last_name, user_email " +
                        "FROM user " +
                        "WHERE 1=1 ");

        List<String> parameters = new ArrayList<>();
        if(searchUserByParams.getFirstName() != null) {
            query.append("AND user_first_name LIKE ? ");
            parameters.add("%" + searchUserByParams.getFirstName() + "%");
        }
        if(searchUserByParams.getLastName() != null) {
            query.append("AND user_last_name LIKE ? ");
            parameters.add("%" + searchUserByParams.getLastName() + "%");
        }
        if(searchUserByParams.getEmail() != null) {
            query.append("AND user_email LIKE ? ");
            parameters.add("%" + searchUserByParams.getEmail() + "%");
        }
        List<EntityUser> users = new ArrayList<>();
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EntityUser user = EntityUser.fromResultSet(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
        return users;
    }

    public boolean updateUser(EntityUser entityUser, UpdateUserParams params) {

        // The Builder pattern was not used here as it was simpler to directly retrieve values based on conditions.
        String query = "UPDATE user SET user_first_name = ?, user_last_name = ?,  user_email = ?, user_active = ?  " +
                "WHERE user_id = ?";

        String firstName =  (params.getFirstName() == null) ? entityUser.getFirstName() : params.getFirstName();
        String lastName =  (params.getLastName() == null) ? entityUser.getLastName() : params.getLastName();
        String newEmail =  (params.getNewEmail() == null) ? entityUser.getEmail() : params.getNewEmail();
        Boolean isUserActive = (params.isUserActive() == null) ? entityUser.isUserActive() : params.isUserActive();

        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, newEmail);
            stmt.setBoolean(4, isUserActive);
            stmt.setInt(5, entityUser.getId());
            int affectedRows = stmt.executeUpdate();
            boolean isUpdateSuccess =  affectedRows > 0;

            if(isUpdateSuccess) {
                entityUser.updateLastName(lastName);
                entityUser.updateFirstName(firstName);
                entityUser.updateUserActive(isUserActive);
                entityUser.updateEmail(newEmail);

            }

            return isUpdateSuccess;
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public List<EntityUser> getAllUsers() {
        String query = "SELECT user_id, user_first_name, user_last_name, user_email FROM user ORDER BY user_id ASC";
        List<EntityUser> users = new ArrayList<>();
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EntityUser user = EntityUser.fromResultSet(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
        return users;
    }




    /**
     * For clear users before simulations
     * @return boolean
     */
    public boolean deleteAllUsers() {
        String query = "DELETE FROM user WHERE user_id > 0";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    public Integer getUserIdByEmail(String email) {
        String query = "SELECT user_id FROM user WHERE user_email = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    public boolean loadModelUserByEmail(EntityUser entityUser, int userId) {
        String query = "SELECT user_id, user_first_name, user_last_name, user_email, user_create_at, user_active " +
                "FROM user " +
                "WHERE user_id = ?";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                //int resUserId = resultSet.getInt("user_id");
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String userEmail = resultSet.getString("user_email");
                Timestamp createAt = resultSet.getTimestamp("user_create_at");
                boolean userActive = resultSet.getBoolean("user_active");

                entityUser.updateId(userId);
                entityUser.updateLastName(lastName);
                entityUser.updateFirstName(firstName);
                entityUser.updateEmail(userEmail);
                entityUser.updateCreateAt(createAt);
                entityUser.updateUserActive(userActive);
                return true;
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public boolean isEmailAlreadyUsed(String email) {
        String query = "SELECT 1 FROM user WHERE user_email = ?";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

}
