package crud.param;

public class ParamsSearchUser {
    private final String email;
    private final String firstName;
    private final String lastName;

    public ParamsSearchUser(Builder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public static class Builder {
        private String email;
        private String firstName;
        private String lastName;


        public Builder withEmail(String email) {
            this.email = email;
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

        public ParamsSearchUser build() {
            return new ParamsSearchUser(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }
}
