package crud.router;

import crud.controller.ControllerUser;
import crud.dao.DaoUser;
import crud.database.DatabaseConnection;
import crud.model.ModelUser;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;
import crud.service.ServiceUser;
import crud.view.ViewUser;

public class RouterUser {

    private final ControllerUser controllerUser;

    public RouterUser() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DaoUser daoUser = new DaoUser(databaseConnection);
        ServiceUser serviceUser = new ServiceUser(daoUser);
        ViewUser viewUser = new ViewUser();
        this.controllerUser = new ControllerUser(serviceUser, viewUser);
    }

    public void addUser(String firstName, String lastName, String email) {
        ModelUser modelUser = new ModelUser(firstName, lastName, email);
        this.controllerUser.addUser(modelUser);
    }

    public void deleteUser(String email) {
        ModelUser modelUser = new ModelUser();
        this.controllerUser.deleteUser(modelUser, email);
    }

    public void updateUser(UpdateUserParams updateUserParams) {
        ModelUser modelUser = new ModelUser();
        this.controllerUser.updateUser(modelUser, updateUserParams);
    }

    public void getAllUsers() {
        this.controllerUser.getAllUsers ();
    }

    public void getUsersBySearch(SearchUserByParams searchUserByParams) {
        this.controllerUser.getUsersBySearch(searchUserByParams);
    }

    public boolean deleteAllUsers() {
        return this.controllerUser.deleteAllUsers();
    }
}
