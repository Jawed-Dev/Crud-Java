package crud.dto;
import java.sql.Timestamp;

public class DtoUser {
    private int id;
    private String lastName;
    private String firstName;
    private String currentEmail;

    private Boolean isUserActive;
    private Timestamp createAt;

    private String newEmail;
    private String newFirstName;
    private String newLastName;

    // constructor
    public DtoUser(String firstName, String lastName, String currentEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentEmail = currentEmail;
    }
    public DtoUser() {}

    // methods
    @Override
    public String toString() {
        return "ModelUser {" +
                "id = " + this.id +
                ", firstName = '" + this.firstName + '\'' +
                ", lastName = '" + this.lastName + '\'' +
                ", email = '" + this.currentEmail + '\'' +
                "}";
    }

    // getters
    public int getId() {
        return this.id;
    }
    public String getCurrentEmail() {
        return this.currentEmail;
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
    public String getNewEmail() {
        return newEmail;
    }
    public String getNewFirstName() {
        return newFirstName;
    }
    public String getNewLastName() {
        return newLastName;
    }


    // setters
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
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

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public void setNewFirstName(String newEmail) {
        this.newFirstName = newEmail;
    }

    public void setNewLastName(String newEmail) {
        this.newLastName = newEmail;
    }

}
