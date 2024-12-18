package crud.router;

import crud.controller.ControllerUser;
import crud.repository.RepositoryUser;
import crud.database.DatabaseConnection;
import crud.dto.DtoResponse;
import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.validation.ValidationUser;
import crud.validation.ValidationFormat;
import crud.entity.EntityUser;
import crud.service.ServiceUser;

public class RouterUser {

    private final ControllerUser controllerUser;

    public RouterUser() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        RepositoryUser repositoryUser = new RepositoryUser(databaseConnection);
        ValidationFormat validationFormat = new ValidationFormat();
        ValidationUser validationUser = new ValidationUser(validationFormat, repositoryUser);
        MapperUser mapperUser = new MapperUser();
        ServiceUser serviceUser = new ServiceUser(repositoryUser, mapperUser, validationUser);
        this.controllerUser = new ControllerUser(serviceUser);
    }

    public DtoResponse<EntityUser> addUser(DtoUser dtoUser) {
        return this.controllerUser.addUser(dtoUser);
    }

    public DtoResponse<EntityUser> deleteUser(DtoUser dtoUser) {
        return this.controllerUser.deleteUser(dtoUser);
    }

    public DtoResponse<EntityUser> updateUser(DtoUser dtoUser) {
        return this.controllerUser.updateUser(dtoUser);
    }

    public void getAllUsers() {
        this.controllerUser.getAllUsers ();
    }

    public void getUsersBySearch(DtoUser dtoUser) {
        this.controllerUser.getUsersBySearch(dtoUser);
    }

    public void deleteAllUsers() {
        this.controllerUser.deleteAllUsers();
    }
}
