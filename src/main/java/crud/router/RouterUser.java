package crud.router;

import crud.controller.ControllerUser;
import crud.dao.DaoUser;
import crud.database.DatabaseConnection;
import crud.validation.ValidationUser;
import crud.validation.ValidationFormat;
import crud.entity.EntityUser;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;
import crud.ServiceUser;
import crud.view.ViewUser;

public class RouterUser {

    private final ControllerUser controllerUser;

    public RouterUser() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DaoUser daoUser = new DaoUser(databaseConnection);
        ValidationFormat validationFormat = new ValidationFormat();
        ValidationUser validationUser = new ValidationUser(validationFormat, daoUser);
        ServiceUser serviceUser = new ServiceUser(daoUser, validationUser);
        ViewUser viewUser = new ViewUser();
        this.controllerUser = new ControllerUser(serviceUser, viewUser);
    }

    public void addUser(String firstName, String lastName, String email) {
        EntityUser entityUser = new EntityUser(firstName, lastName, email);
        this.controllerUser.addUser(entityUser);
    }

    public void deleteUser(String email) {
        EntityUser entityUser = new EntityUser();
        this.controllerUser.deleteUser(entityUser, email);
    }

    public void updateUser(UpdateUserParams updateUserParams) {
        EntityUser entityUser = new EntityUser();
        this.controllerUser.updateUser(entityUser, updateUserParams);
    }

    public void getAllUsers() {
        this.controllerUser.getAllUsers ();
    }

    public void getUsersBySearch(SearchUserByParams searchUserByParams) {
        this.controllerUser.getUsersBySearch(searchUserByParams);
    }

    public void deleteAllUsers() {
        this.controllerUser.deleteAllUsers();
    }
}
