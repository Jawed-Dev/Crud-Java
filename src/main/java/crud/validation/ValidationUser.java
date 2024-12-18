package crud.validation;
import crud.repository.RepositoryUser;
import crud.dto.DtoUser;
import crud.entity.EntityUser;

public class ValidationUser {
    private final ValidationFormat validationFormat;
    private final RepositoryUser repositoryUser;

    // msg Errors
    public static String ERR_EMAIL_USED = "Cet email est déjà utilisé par un utilisateur.";
    public static String ERR_EMAIL_NOT_USED = "Cet email n'est utilisé par aucun utilisateur.";
    public static String ERR_REPOSITORY_USER = "Une erreur est survenue du résultat du Repository.";
    public static String ERR_EMAIL_INVALID = "Le format de cet email est invalide.";
    public static String ERR_FIRSTNAME_INVALID = "Le format du prénom est invalide..";
    public static String ERR_LASTNAME_INVALID = "Le format du nom est invalide.";
    public static String ERR_ID_USER_INVALID = "Le format de l'identifiant de l'utilisateur est invalide.";
    public static String ERR_FORMS_EMPTY = "Tous les champs sont vides";
    public static String ERR_DEPENDENCIES = "Une erreur est survenue au niveau des dépendances";
    public static String ERR_MAPPER = "Une erreur est survenue au niveau du Mapper User";

    public ValidationUser(ValidationFormat validationFormat, RepositoryUser repositoryUser) {
        this.validationFormat = validationFormat;
        this.repositoryUser = repositoryUser;
    }

    public ValidationResult validateAddUser(DtoUser dtoUser) {
        if(!this.areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(repositoryUser.isEmailAlreadyUsed(dtoUser.getCurrentEmail())) {
            return new ValidationResult(false, ERR_EMAIL_USED);
        }
        if(!validationFormat.isValidFirstName(dtoUser.getFirstName())) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(!validationFormat.isValidLastName(dtoUser.getLastName())) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }
        if(!validationFormat.isValidEmail(dtoUser.getCurrentEmail())) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateDeleteUser(DtoUser dtoUser) {
        if(!this.areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(!validationFormat.isValidFirstName(dtoUser.getFirstName())) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(!validationFormat.isValidLastName(dtoUser.getLastName())) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }
        if(!validationFormat.isValidEmail(dtoUser.getCurrentEmail())) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(!repositoryUser.isEmailAlreadyUsed(dtoUser.getCurrentEmail())) {
            return new ValidationResult(false, ERR_EMAIL_NOT_USED);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateUpdateUser(DtoUser dtoUser) {
        String currentEmail = dtoUser.getCurrentEmail();
        String newEmail = dtoUser.getNewEmail();
        String firstName = dtoUser.getFirstName();
        String lastName = dtoUser.getLastName();

        if(!this.areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(currentEmail == null && firstName == null && lastName == null) {
            return new ValidationResult(false, ERR_FORMS_EMPTY);
        }
        if(!repositoryUser.isEmailAlreadyUsed(currentEmail)) {
            return new ValidationResult(false, ERR_EMAIL_NOT_USED);
        }
        if(currentEmail != null && !validationFormat.isValidEmail(currentEmail)) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(newEmail != null && !validationFormat.isValidEmail(newEmail)) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(firstName != null && !validationFormat.isValidFirstName(firstName)) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(lastName != null && !validationFormat.isValidLastName(lastName)) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }

        return new ValidationResult(true);
    }

    public ValidationResult validateGetUsersBySearch(DtoUser dtoUser) {
        String email = dtoUser.getCurrentEmail();
        String firstName = dtoUser.getFirstName();
        String lastName = dtoUser.getLastName();

        if(!this.areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(email.isEmpty() && firstName.isEmpty() && lastName.isEmpty()) {
            return new ValidationResult(false, ERR_FORMS_EMPTY);
        }
        if(!email.isEmpty() && !validationFormat.isValidEmail(email)) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(!firstName.isEmpty() && !validationFormat.isValidFirstName(firstName)) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(!lastName.isEmpty() && !validationFormat.isValidLastName(lastName)) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }

        return new ValidationResult(true);
    }

    public ValidationResult validateResultMapper(EntityUser entityUser) {
        if(!validationFormat.isValidResultMapper(entityUser)) {
            return new ValidationResult(false, ERR_MAPPER);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateIdUser(Integer idUser) {
        if(!validationFormat.isValidId(idUser)) {
            return new ValidationResult(false, ERR_ID_USER_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateResultRepository(EntityUser entityUser) {
        if(!this.isValidResultRepositoryUser(entityUser)) {
            return new ValidationResult(false, ERR_REPOSITORY_USER);
        }
        return new ValidationResult(true);
    }

    private boolean areDependenciesValid() {
        return this.validationFormat != null && this.repositoryUser != null;
    }

    private boolean isValidResultRepositoryUser(EntityUser entityUser) {
        if(entityUser == null) return false;
        int idUser = entityUser.getId();
        String firstName = entityUser.getFirstName();
        String lastName = entityUser.getLastName();
        String email = entityUser.getEmail();

        int MIN_ID_USER_VALID = 1;

        if(idUser < MIN_ID_USER_VALID) return false;
        if(firstName == null || firstName.isEmpty()) return false;
        if(lastName == null || lastName.isEmpty()) return false;
        if(email == null || email.isEmpty()) return false;

        return true;
    }
}
