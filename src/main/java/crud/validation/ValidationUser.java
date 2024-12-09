package crud.validation;
import crud.dao.DaoUser;
import crud.dto.DtoUser;
import crud.entity.EntityUser;

public class ValidationUser {
    private final ValidationFormat validationFormat;
    private final DaoUser daoUser;

    // msg Errors
    public static String ERR_EMAIL_USED = "Cet email est déjà utilisé par un utilisateur.";
    public static String ERR_EMAIL_NOT_USED = "Cet email n'est utilisé par aucun utilisateur.";
    public static String ERR_DAO_ADD_USER = "Une erreur est survenue pour l'ajout de l'utilisateur.";
    public static String ERR_DAO_DELETE_USER = "Une erreur est survenue pour la suppréssion de l'utilisateur.";
    public static String ERR_DAO_UPDATE_USER = "Une erreur est survenue pour la mise à jour de l'utilisateur.";
    public static String ERR_EMAIL_INVALID = "Le format de cet email est invalide.";
    public static String ERR_FIRSTNAME_INVALID = "Le format du prénom est invalide..";
    public static String ERR_LASTNAME_INVALID = "Le format du nom est invalide.";
    public static String ERR_ID_USER_INVALID = "Le format de l'identifiant de l'utilisateur est invalide.";
    public static String ERR_FORMS_EMPTY = "Tous les champs sont vides";
    public static String ERR_DEPENDENCIES = "Une erreur est survenue au niveau des dépendances";
    public static String ERR_ENTITY_INVALID = "Une erreur est survenue au niveau de l'entité User";

    public ValidationUser(ValidationFormat validationFormat, DaoUser daoUser) {
        this.validationFormat = validationFormat;
        this.daoUser = daoUser;
    }

    public ValidationResult validateAddUser(DtoUser dtoUser) {
        if(!areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(daoUser.isEmailAlreadyUsed(dtoUser.getCurrentEmail())) {
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
        if(!areDependenciesValid()) {
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
        if(!daoUser.isEmailAlreadyUsed(dtoUser.getCurrentEmail())) {
            return new ValidationResult(false, ERR_EMAIL_NOT_USED);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateUpdateUser(DtoUser dtoUser) {
        String currentEmail = dtoUser.getCurrentEmail();
        String newEmail = dtoUser.getNewEmail();
        String firstName = dtoUser.getFirstName();
        String lastName = dtoUser.getLastName();

        if(!areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(!daoUser.isEmailAlreadyUsed(currentEmail)) {
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
        if(currentEmail == null && firstName == null && lastName == null) {
            return new ValidationResult(false, ERR_FORMS_EMPTY);
        }

        return new ValidationResult(true);
    }

    public ValidationResult validateGetUsersBySearch(DtoUser dtoUser) {
        String email = dtoUser.getCurrentEmail();
        String firstName = dtoUser.getFirstName();
        String lastName = dtoUser.getLastName();

        if(!areDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(email != null && !validationFormat.isValidEmail(email)) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(firstName != null && !validationFormat.isValidFirstName(firstName)) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(lastName != null && !validationFormat.isValidLastName(lastName)) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }
        if(email == null && firstName == null && lastName == null) {
            return new ValidationResult(false, ERR_FORMS_EMPTY);
        }

        return new ValidationResult(true);
    }

    public ValidationResult validateGetUserIdByEmail(Integer idUser) {
        if(!validationFormat.isValidId(idUser)) {
            return new ValidationResult(false, ERR_ID_USER_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateResultMapper(EntityUser entityUser) {
        if(!validationFormat.isValidResultMapper(entityUser)) {
            return new ValidationResult(false, ERR_ENTITY_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateIdUser(Integer idUser) {
        if(!validationFormat.isValidId(idUser)) {
            return new ValidationResult(false, ERR_ID_USER_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateResultDao(EntityUser entityUser) {
        if(!validationFormat.isValidResultDao(entityUser)) {
            return new ValidationResult(false, ERR_DAO_ADD_USER);
        }
        return new ValidationResult(true);
    }

    private boolean areDependenciesValid() {
        return this.validationFormat != null && this.daoUser != null;
    }
}
