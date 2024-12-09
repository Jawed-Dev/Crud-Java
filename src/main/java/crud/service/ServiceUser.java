package crud.service;
import crud.dao.DaoUser;
import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.validation.ValidationUser;
import crud.validation.ValidationResult;
import crud.entity.EntityUser;

import java.util.List;

public class ServiceUser {
    private final DaoUser daoUser;
    private final MapperUser mapperUser;
    private final ValidationUser validationUser;
    public ServiceUser(DaoUser daoUser, MapperUser mapperUser, ValidationUser validationUser) {
        this.daoUser = daoUser;
        this.mapperUser = mapperUser;
        this.validationUser = validationUser;
    }

    public EntityUser addUser(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateAddUser(dtoUser);
            if(!isValidationSuccess(dtoValidation)) return null;

            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);
            ValidationResult mapperValidation = validationUser.validateResultMapper(entityUser);
            if(!isValidationSuccess(mapperValidation)) return null;

            entityUser = daoUser.addUser(entityUser);
            ValidationResult daoValidation = validationUser.validateResultDao(entityUser);
            if(!isValidationSuccess(daoValidation)) return null;
            return entityUser;
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public EntityUser deleteUser(DtoUser dtoUser) {
        try {

            ValidationResult dtoValidation = validationUser.validateDeleteUser(dtoUser);
            if(!isValidationSuccess(dtoValidation)) return null;

            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);
            ValidationResult entityValidation = validationUser.validateResultMapper(entityUser);
            if(!isValidationSuccess(entityValidation)) return null;

            Integer idUser = daoUser.getUserIdByEmail(entityUser.getEmail());
            ValidationResult idUserValidation = validationUser.validateIdUser(idUser);
            if(!isValidationSuccess(idUserValidation)) return null;

            entityUser.setId(idUser);

            entityUser = daoUser.deleteUser(entityUser);
            ValidationResult daoValidation = validationUser.validateResultDao(entityUser);
            if(!isValidationSuccess(daoValidation)) return null;

            return entityUser;
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public EntityUser updateUser(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateUpdateUser(dtoUser);
            if(!isValidationSuccess(dtoValidation)) return null;

            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);
            ValidationResult mapperValidation = validationUser.validateResultMapper(entityUser);
            if(!isValidationSuccess(mapperValidation)) return null;

            Integer idUser = daoUser.getUserIdByEmail(entityUser.getEmail());
            ValidationResult idUserValidation = validationUser.validateIdUser(idUser);
            if(!isValidationSuccess(idUserValidation)) return null;
            entityUser.setId(idUser);

            if(dtoUser.getNewEmail() != null) entityUser.setEmail(dtoUser.getNewEmail());
            if(dtoUser.getNewFirstName() != null) entityUser.setFirstName(dtoUser.getNewFirstName());
            if(dtoUser.getNewLastName() != null) entityUser.setLastName(dtoUser.getNewLastName());

            entityUser = daoUser.updateUser(entityUser);
            ValidationResult daoValidation = validationUser.validateIdUser(idUser);
            if(!isValidationSuccess(daoValidation)) return null;
            return entityUser;
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<EntityUser> getUsersBySearch(DtoUser dtoUser) {
        ValidationResult dtoValidation = validationUser.validateGetUsersBySearch(dtoUser);
        if(!isValidationSuccess(dtoValidation)) return null;
        return this.daoUser.getUsersBySearch(dtoUser);
    }


    public List<EntityUser> getAllUsers() {
        return this.daoUser.getAllUsers();
    }


    public boolean deleteAllUsers() {
        return this.daoUser.deleteAllUsers();
    }

    private boolean isValidationSuccess (ValidationResult validationResult) {
        if (!validationResult.isValid()) {
            System.out.println(validationResult.getErrorMessage());
            return false;
        }
        return true;
    }
}

