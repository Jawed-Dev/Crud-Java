package crud.repository;
import crud.database.DatabaseConnection;
import crud.dto.DtoUser;
import crud.entity.EntityUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUser {
    private final DatabaseConnection databaseConnection;

    public RepositoryUser(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public EntityUser add(EntityUser entityUser) {
        String query = "INSERT INTO user (user_first_name, user_last_name, user_email, user_create_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entityUser.getFirstName());
            preparedStatement.setString(2, entityUser.getLastName());
            preparedStatement.setString(3, entityUser.getEmail());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(4, timestamp);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new RepositoryException("Échec de la création de l'utilisateur.");
            }

            // for set userID in Entity
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new RepositoryException("Impossible de récupérer l'ID de l'utilisateur.");
                }
                entityUser.setId(generatedKeys.getInt(1));
            }
            return entityUser;
        }
        catch (SQLException e) {
            throw new RepositoryException("Erreur SQL lors de la sauvegarde de l'utilisateur.", e);
        }
    }

    public EntityUser delete(EntityUser entityUser) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entityUser.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
                throw new RepositoryException("Échec de la suppression de l'utilisateur.");
            }
            return entityUser;
        }
        catch (SQLException e) {
            throw new RepositoryException("Erreur SQL lors de la suppression de l'utilisateur.", e);
        }
    }

    public List<EntityUser> findByFilters(DtoUser dtoUser) {
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
            throw new RepositoryException("Erreur SQL lors de la recherche d'une liste d'utilisateurs.", e);
        }
        return users;
    }

    public EntityUser update(EntityUser entityUser) {
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
            if(!isUpdateSuccess) {
                throw new RepositoryException("Échec de la mise à jour de l'utilisateur.");
            }
            return entityUser;
        }
        catch (SQLException e) {
            throw new RepositoryException("Erreur SQL lors de la mise à jour de l'utilsateur.", e);
        }
    }

    public List<EntityUser> findAll() {
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
            throw new RepositoryException("Erreur SQL lors de la sauvegarde", e);
        }
        return users;
    }


    /**
     * For clear users before simulations
     * @return Integer
     */
    public Integer deleteAll() {
        String query = "DELETE FROM user WHERE user_id > 0";
        try(Connection connection = this.databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RepositoryException("Erreur SQL lors de la suppression de tous les utilisateurs.", e);
        }
    }

    public Integer getIdByEmail(String email) {
        String query = "SELECT user_id FROM user WHERE user_email = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new RepositoryException("Aucun utilisateur trouvé avec l'email : " + email);
            }
            return resultSet.getInt("user_id");

        }
        catch (SQLException e) {
            throw new RepositoryException("Échec de la récupérer de l'ID de l'utilisateur.", e);
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
            throw new RepositoryException("Échec de la vérification pour savoir si l'email est déjà utilisé.", e);
        }
    }
}
