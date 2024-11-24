package crud.entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EntityUser {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private Boolean isUserActive;
    private Timestamp createAt;


    // constructor
    public EntityUser(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isUserActive = true;
    }

    public EntityUser() {}

    // methods
    @Override
    public String toString() {
        return "ModelUser {" +
            "id = " + this.id +
            ", firstName = '" + this.firstName + '\'' +
            ", lastName = '" + this.lastName + '\'' +
            ", email = '" + this.email + '\'' +
            "}";
    }



    public static EntityUser fromResultSet(ResultSet resultSet) throws SQLException {
        EntityUser user = new EntityUser();
        user.updateId(resultSet.getInt("user_id"));
        user.updateLastName(resultSet.getString("user_last_name"));
        user.updateFirstName(resultSet.getString("user_first_name"));
        user.updateEmail(resultSet.getString("user_email"));
        return user;
    }

    // getters
    public int getId() {
        return this.id;
    }
    public String getEmail() {
        return this.email;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public Boolean isUserActive() {
        return this.isUserActive;
    }

    // setters
    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateId(int id) {
        this.id = id;
    }

    public void updateUserActive(boolean active) {
        this.isUserActive = active;
    }

    public void updateCreateAt(Timestamp timestamp) {
        this.createAt = timestamp;
    }

}
