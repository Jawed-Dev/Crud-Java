package crud.dao;
import crud.database.DatabaseConnection;
import crud.model.ModelUser;
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

    public boolean addUser(ModelUser modelUser) {
        String query = "INSERT INTO user (user_last_name, user_first_name, user_email, user_create_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, modelUser.getLastName());
            preparedStatement.setString(2, modelUser.getFirstName());
            preparedStatement.setString(3, modelUser.getEmail());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(4, timestamp);

            int affectedRows = preparedStatement.executeUpdate();

            // set userID in Model
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) throw new SQLException("ID user non obtenu.");
                modelUser.updateId(generatedKeys.getInt(1));
            }

            return affectedRows > 0;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return false;
        }
    }

    public boolean deleteUser(ModelUser modelUser, String email) {
        String query = "DELETE FROM user WHERE user_email = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, email);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
        catch (SQLException error) {
            System.err.println("SQL Error: " + error.getMessage());
            return false;
        }
    }

    public boolean loadUserByEmail(ModelUser modelUser, String email) {
        String query = "SELECT user_id, user_last_name, user_first_name, user_email, user_create_at, user_active " +
                "FROM user " +
                "WHERE user_email = ?";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String firstName = resultSet.getString("user_first_name");
                String lastName = resultSet.getString("user_last_name");
                String userEmail = resultSet.getString("user_email");
                Timestamp createAt = resultSet.getTimestamp("user_create_at");
                boolean userActive = resultSet.getBoolean("user_active");

                modelUser.updateId(userId);
                modelUser.updateLastName(lastName);
                modelUser.updateFirstName(firstName);
                modelUser.updateEmail(userEmail);
                modelUser.updateCreateAt(createAt);
                modelUser.updateUserActive(userActive);
                return true;
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public boolean isUserExistingByEmail(String email) {
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

    public boolean updateEmail(ModelUser modelUser, UpdateUserParams params) {
        String query = "UPDATE user SET user_last_name = ?, user_first_name = ?, user_email = ?, user_active = ?  " +
                "WHERE user_email = ?";

        String lastName =  (params.getLastName() == null) ? modelUser.getLastName() : params.getLastName();
        String firstName =  (params.getFirstName() == null) ? modelUser.getFirstName() : params.getFirstName();
        String newEmail =  (params.getNewEmail() == null) ? modelUser.getEmail() : params.getNewEmail();
        Boolean isUserActive = (params.isUserActive() == null) ? modelUser.isUserActive() : params.isUserActive();

        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            stmt.setString(3, newEmail);
            stmt.setBoolean(4, isUserActive);
            stmt.setString(5, modelUser.getEmail());
            int affectedRows = stmt.executeUpdate();
            boolean isUpdateSuccess =  affectedRows > 0;

            if(isUpdateSuccess) {
                modelUser.updateLastName(lastName);
                modelUser.updateFirstName(firstName);
                modelUser.updateUserActive(isUserActive);
                modelUser.updateEmail(newEmail);

            }

            return isUpdateSuccess;
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public List<ModelUser> getAllUsers() {
        String query = "SELECT user_id, user_last_name, user_first_name, user_email FROM user ORDER BY user_id ASC";
        List<ModelUser> users = new ArrayList<>();
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ModelUser user = ModelUser.fromResultSet(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
        return users;
    }

    public List<ModelUser> getUsersBySearch(SearchUserByParams searchUserByParams) {
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
        List<ModelUser> users = new ArrayList<>();
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ModelUser user = ModelUser.fromResultSet(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return null;
        }
        return users;
    }
}
