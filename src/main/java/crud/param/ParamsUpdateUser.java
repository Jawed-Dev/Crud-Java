package crud.param;

public class ParamsUpdateUser {
    private final String newEmail;
    private final String firstName;
    private final String lastName;
    private final Boolean userActive;
    private final String currentEmail;

    private ParamsUpdateUser(Builder builder) {
        this.newEmail = builder.newEmail;
        this.currentEmail = builder.currentEmail;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userActive = builder.userActive;
    }

    public static class Builder {
        private String newEmail;
        private String firstName;
        private String lastName;
        private Boolean userActive;
        private String currentEmail;

        public Builder withNewEmail(String newEmail) {
            this.newEmail = newEmail;
            return this;
        }
        public Builder withCurrentEmail(String currentEmail) {
            this.currentEmail = currentEmail;
            return this;
        }
        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder withUserActive(Boolean userActive) {
            this.userActive = userActive;
            return this;
        }

        public ParamsUpdateUser build() {
            return new ParamsUpdateUser(this);
        }
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean isUserActive() {
        return userActive;
    }
}
