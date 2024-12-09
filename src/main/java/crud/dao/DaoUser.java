package crud.dao;
import crud.database.DatabaseConnection;
import crud.dto.DtoUser;
import crud.entity.EntityUser;
import crud.param.ParamsUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoUser {
    private final DatabaseConnection databaseConnection;

    public DaoUser(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public EntityUser addUser(EntityUser entityUser) {
        String query = "INSERT INTO user (user_first_name, user_last_name, user_email, user_create_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entityUser.getFirstName());
            preparedStatement.setString(2, entityUser.getLastName());
            preparedStatement.setString(3, entityUser.getEmail());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(4, timestamp);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) throw new SQLException("Erreur dans l'obtention de l'ID de l'utilisateur.");

            // set userID in Entity
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) throw new SQLException("ID user non obtenu.");
                entityUser.setId(generatedKeys.getInt(1));
            }

            return entityUser;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return null;
        }
    }

    public EntityUser deleteUser(EntityUser entityUser) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entityUser.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) return null;
            return entityUser;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return null;
        }
    }

    public List<EntityUser> getUsersBySearch(DtoUser dtoUser) {
        /*
         * This method uses Builder pattern to create a dynamic SQL,
         * Depending on which parameters are provided, additional WHERE conditions are appended to the query.
         */
        StringBuilder query = new StringBuilder(
                "SELECT user_id, user_first_name, user_last_name, user_email " +
                        "FROM user " +
                        "WHERE 1=1 ");

        List<String> parameters = new ArrayList<>();
        if(dtoUser.getFirstName() != null) {
            query.append("AND user_first_name LIKE ? ");
            parameters.add("%" + dtoUser.getFirstName() + "%");
        }
        if(dtoUser.getLastName() != null) {
            query.append("AND user_last_name LIKE ? ");
            parameters.add("%" + dtoUser.getLastName() + "%");
        }
        if(dtoUser.getCurrentEmail() != null) {
            query.append("AND user_email LIKE ? ");
            parameters.add("%" + dtoUser.getCurrentEmail() + "%");
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

    public EntityUser updateUser(EntityUser entityUser) {

        // The Builder pattern was not used here as it was simpler to directly retrieve values based on conditions.
        String query = "UPDATE user SET user_first_name = ?, user_last_name = ?, user_email = ? " +
                        "WHERE user_id = ?";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, entityUser.getFirstName());
            stmt.setString(2, entityUser.getLastName());
            stmt.setString(3, entityUser.getEmail());
            stmt.setInt(4, entityUser.getId());

            int affectedRows = stmt.executeUpdate();
            boolean isUpdateSuccess =  affectedRows > 0;
            if(!isUpdateSuccess) return null;
            return entityUser;
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
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

    public DtoUser loadModelUserByEmail(Integer userId) {
        String query = "SELECT user_id, user_first_name, user_last_name, user_email, user_create_at, user_active " +
                "FROM user " +
                "WHERE user_id = ?";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String userEmail = resultSet.getString("user_email");
                Timestamp createAt = resultSet.getTimestamp("user_create_at");
                boolean userActive = resultSet.getBoolean("user_active");

                DtoUser dtoUser = new DtoUser();

                dtoUser.setLastName(lastName);
                dtoUser.setFirstName(firstName);
                dtoUser.setCurrentEmail(userEmail);
                dtoUser.setCreateAt(createAt);
                dtoUser.setUserActive(userActive);

                return dtoUser;
            }
            return null;
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
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
