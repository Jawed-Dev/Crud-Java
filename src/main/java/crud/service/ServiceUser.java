package crud.service;
import crud.repository.RepositoryException;
import crud.repository.RepositoryUser;
import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.validation.ValidationUser;
import crud.validation.ValidationResult;
import crud.entity.EntityUser;

import java.util.List;

public class ServiceUser {
    private final RepositoryUser repositoryUser;
    private final MapperUser mapperUser;
    private final ValidationUser validationUser;

    public ServiceUser(RepositoryUser repositoryUser, MapperUser mapperUser, ValidationUser validationUser) {
        this.repositoryUser = repositoryUser;
        this.mapperUser = mapperUser;
        this.validationUser = validationUser;
    }

    public ServiceResult<EntityUser> addUser(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateAddUser(dtoUser);
            if(!dtoValidation.isValid()) {
                return new ServiceResult<>("Validation Error: " + dtoValidation.getErrorMessage());
            }
            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);
            entityUser = repositoryUser.add(entityUser);
            return new ServiceResult<>(entityUser);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }

    public ServiceResult<EntityUser> deleteUser(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateDeleteUser(dtoUser);
            if(!dtoValidation.isValid()) {
                return new ServiceResult<>("Validation Error: " + dtoValidation.getErrorMessage());
            }

            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);

            Integer idUser = repositoryUser.getIdByEmail(entityUser.getEmail());

            entityUser.setId(idUser);
            entityUser = repositoryUser.delete(entityUser);
            return new ServiceResult<>(entityUser);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }

    public ServiceResult<EntityUser> updateUser(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateUpdateUser(dtoUser);
            if(!dtoValidation.isValid()) {
                return new ServiceResult<>("Validation Error: " + dtoValidation.getErrorMessage());
            }

            EntityUser entityUser = mapperUser.dtoToEntityUser(dtoUser);

            Integer idUser = repositoryUser.getIdByEmail(entityUser.getEmail());

            entityUser.setId(idUser);

            if(dtoUser.getNewEmail() != null) entityUser.setEmail(dtoUser.getNewEmail());
            if(dtoUser.getNewFirstName() != null) entityUser.setFirstName(dtoUser.getNewFirstName());
            if(dtoUser.getNewLastName() != null) entityUser.setLastName(dtoUser.getNewLastName());

            entityUser = repositoryUser.update(entityUser);
            return new ServiceResult<>(entityUser);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }

    public ServiceResult<List<EntityUser>> getUsersBySearch(DtoUser dtoUser) {
        try {
            ValidationResult dtoValidation = validationUser.validateGetUsersBySearch(dtoUser);
            if(!dtoValidation.isValid()) {
                return new ServiceResult<>("Validation Error: " + dtoValidation.getErrorMessage());
            }
            List<EntityUser> users = this.repositoryUser.findByFilters(dtoUser);
            return new ServiceResult<>(users);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }


    public ServiceResult<List<EntityUser>> getAllUsers() {
        try {
            List<EntityUser> users = this.repositoryUser.findAll();
            return new ServiceResult<>(users);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }


    public ServiceResult<Integer> deleteAllUsers() {
        try {
            Integer amountUsersDeletes = this.repositoryUser.deleteAll();
            return new ServiceResult<>(amountUsersDeletes);
        }
        catch (RepositoryException e) {
            return new ServiceResult<>("Repository Error: " + e.getMessage());
        }
        catch (Exception e) {
            return new ServiceResult<>("Error: " + e.getMessage());
        }
    }

}

