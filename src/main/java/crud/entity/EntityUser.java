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
        user.setId(resultSet.getInt("user_id"));
        user.setLastName(resultSet.getString("user_last_name"));
        user.setFirstName(resultSet.getString("user_first_name"));
        user.setEmail(resultSet.getString("user_email"));
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
    public Timestamp getCreateAt() {
        return createAt;
    }

    // setters
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserActive(boolean active) {
        this.isUserActive = active;
    }

    public void setCreateAt(Timestamp timestamp) {
        this.createAt = timestamp;
    }

}
