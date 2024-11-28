package crud.validation;
import crud.dao.DaoUser;
import crud.entity.EntityUser;
import crud.param.ParamsSearchUser;
import crud.param.ParamsUpdateUser;

public class ValidationUser {
    private final ValidationFormat validationFormat;
    private final DaoUser daoUser;

    // msg Errors
    public static String ERR_EMAIL_USED = "Cet email est déjà utilisé par un utilisateur.";
    public static String ERR_EMAIL_NOT_USED = "Cet email n'est utilisé par aucun utilisateur.";
    public static String ERR_ADD_USER = "Une erreur est survenue pour l'ajout de l'utilisateur.";
    public static String ERR_DELETE_USER = "Une erreur est survenue pour la suppréssion de l'utilisateur.";
    public static String ERR_UPDATE_USER = "Une erreur est survenue pour la mise à jour de l'utilisateur.";
    public static String ERR_LOAD_MODEL_USER = "Une erreur est survenue pour le chargement de l'entité Model User";
    public static String ERR_EMAIL_INVALID = "Le format de cet email est invalide.";
    public static String ERR_FIRSTNAME_INVALID = "Le format du prénom est invalide..";
    public static String ERR_LASTNAME_INVALID = "Le format du nom est invalide.";
    public static String ERR_ID_USER_INVALID = "Le format de l'identifiant de l'utilisateur est invalide.";
    public static String ERR_FORMS_EMPTY = "Tous les champs sont vides";
    public static String ERR_DAO_NOT_SUCCESS = "Une erreur est survenue avec la requête DAO";
    public static String ERR_DEPENDENCIES = "Une erreur est survenue au niveau des dépendances";
    public static String ERR_ENTITY_USER = "Une erreur est survenue au niveau de l'entité User";
    public static int MINIMUM_ID_USER = 1;

    public ValidationUser(ValidationFormat validationFormat, DaoUser daoUser) {
        this.validationFormat = validationFormat;
        this.daoUser = daoUser;
    }

    public ValidationResult validateAddUser(EntityUser entityUser) {
        if(!AreDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(!validationFormat.isValidEntityUser(entityUser)) {
            return new ValidationResult(false, ERR_ENTITY_USER);
        }
        if(daoUser.isEmailAlreadyUsed(entityUser.getEmail())) {
            return new ValidationResult(false, ERR_EMAIL_USED);
        }
        if(!validationFormat.isValidFirstName(entityUser.getFirstName())) {
            return new ValidationResult(false, ERR_FIRSTNAME_INVALID);
        }
        if(!validationFormat.isValidLastName(entityUser.getLastName())) {
            return new ValidationResult(false, ERR_LASTNAME_INVALID);
        }
        if(!validationFormat.isValidEmail(entityUser.getEmail())) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateDeleteUser(String email) {
        if(!AreDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(!validationFormat.isValidEmail(email)) {
            return new ValidationResult(false, ERR_EMAIL_INVALID);
        }
        if(!daoUser.isEmailAlreadyUsed(email)) {
            return new ValidationResult(false, ERR_EMAIL_NOT_USED);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateUpdateUser(ParamsUpdateUser paramsUpdateUser) {
        String currentEmail = paramsUpdateUser.getCurrentEmail();
        String firstName = paramsUpdateUser.getFirstName();
        String lastName = paramsUpdateUser.getLastName();

        if(!AreDependenciesValid()) {
            return new ValidationResult(false, ERR_DEPENDENCIES);
        }
        if(!daoUser.isEmailAlreadyUsed(currentEmail)) {
            return new ValidationResult(false, ERR_EMAIL_NOT_USED);
        }
        if(currentEmail != null && !validationFormat.isValidEmail(currentEmail)) {
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

    public ValidationResult validateGetUsersBySearch(ParamsSearchUser paramsSearchUser) {
        String email = paramsSearchUser.getEmail();
        String firstName = paramsSearchUser.getFirstName();
        String lastName = paramsSearchUser.getLastName();

        if(!AreDependenciesValid()) {
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
        if(idUser < MINIMUM_ID_USER) {
            return new ValidationResult(false, ERR_ID_USER_INVALID);
        }
        return new ValidationResult(true);
    }

    private boolean AreDependenciesValid() {
        return this.validationFormat != null && this.daoUser != null;
    }
}
