package crud.param;

public class UpdateUserParams {
    private final String newEmail;
    private final String firstName;
    private final String lastName;
    private final Boolean userActive;
    private final String oldEmail;

    private UpdateUserParams(Builder builder) {
        this.newEmail = builder.newEmail;
        this.oldEmail = builder.oldEmail;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userActive = builder.userActive;
    }

    public static class Builder {
        private String newEmail;
        private String firstName;
        private String lastName;
        private Boolean userActive;
        private String oldEmail;

        public Builder withNewEmail(String newEmail) {
            this.newEmail = newEmail;
            return this;
        }
        public Builder withOldEmail(String oldEmail) {
            this.oldEmail = oldEmail;
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

        public UpdateUserParams build() {
            return new UpdateUserParams(this);
        }
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getOldEmail() {
        return oldEmail;
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
