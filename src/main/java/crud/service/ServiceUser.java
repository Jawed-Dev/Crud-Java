package crud.service;
import crud.dao.DaoUser;
import crud.validation.ValidationUser;
import crud.validation.ValidationResult;
import crud.entity.EntityUser;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;

import java.util.List;

public class ServiceUser {
    private final DaoUser daoUser;
    private final ValidationUser validationUser;
    public ServiceUser(DaoUser daoUser, ValidationUser validationUser) {
        this.daoUser = daoUser;
        this.validationUser = validationUser;
    }

    public boolean addUser(EntityUser entityUser) {
        try {
            ValidationResult stateValidation = validationUser.validateAddUser(entityUser);
            if(!stateValidation.isValid()) {
                System.out.println(stateValidation.getErrorMessage());
                return false;
            }
            boolean isRequestDBSuccess = daoUser.addUser(entityUser);
            if(!isRequestDBSuccess) {
                System.out.println(ValidationUser.ERR_ADD_USER);
                return false;
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }

    /**
     *
     * @param entityUser The user entity used for loading the user details from the database.
     *                    This object is populated with the user's data if successfully loaded,
     *                    which can be used in views or for additional processing.
     */
    public boolean deleteUser(EntityUser entityUser, String email) {
        try {
            ValidationResult stateValidation = validationUser.validateDeleteUser(email);
            if(!stateValidation.isValid()) {
                System.out.println(stateValidation.getErrorMessage());
                return false;
            }

            if(!this.loadModelUserByEmail(entityUser, email)) {
                System.out.println(ValidationUser.ERR_LOAD_MODEL_USER);
                return false;
            }

            boolean isRequestDBSuccess = daoUser.deleteUser(entityUser);
            if(!isRequestDBSuccess) {
                System.out.println(ValidationUser.ERR_DELETE_USER);
                return false;
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }

    /**
     *
     * @param entityUser The user entity used for loading the user details from the database.
     *                    This object is populated with the user's data if successfully loaded,
     *                    which can be used in views or for additional processing.
     */
    public boolean updateUser(EntityUser entityUser, UpdateUserParams params) {
        try {
            ValidationResult stateValidation = validationUser.validateUpdateUser(params);
            if(!stateValidation.isValid()) {
                System.out.println(stateValidation.getErrorMessage());
                return false;
            }

            if(!this.loadModelUserByEmail(entityUser, params.getCurrentEmail())) {
                System.out.print(ValidationUser.ERR_LOAD_MODEL_USER);
                return false;
            }

            boolean isRequestDBSuccess = daoUser.updateUser(entityUser, params);
            if(!isRequestDBSuccess) {
                System.out.print(ValidationUser.ERR_UPDATE_USER);
                return false;
            }
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }

    public List<EntityUser> getAllUsers() {
        return this.daoUser.getAllUsers();
    }

    public List<EntityUser> getUsersBySearch(SearchUserByParams searchUserByParams) {
        if(!validationUser.validateGetUsersBySearch(searchUserByParams).isValid()) return null;
        return this.daoUser.getUsersBySearch(searchUserByParams);
    }

    public boolean deleteAllUsers() {
        return this.daoUser.deleteAllUsers();
    }

    public Integer getUserIdByEmail(String email) {
        try {
            Integer idUser = daoUser.getUserIdByEmail(email);
            ValidationResult stateValidation = validationUser.validateGetUserIdByEmail(idUser);
            if(!stateValidation.isValid()) return null;
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    public boolean loadModelUserByEmail(EntityUser entityUser, String email) {
        try {
            // Cause Model not loaded before, we need user ID sql here
            int userId = this.getUserIdByEmail(email);
            if(userId < 1) return false;

            return daoUser.loadModelUserByEmail(entityUser, userId);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }
}

