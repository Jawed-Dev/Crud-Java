package www.crud.router;

import www.crud.controller.ControllerUser;
import www.crud.dao.DaoUser;
import www.crud.database.DatabaseConnection;
import www.crud.model.ModelUser;
import www.crud.param.UpdateUserParams;
import www.crud.service.ServiceUser;
import www.crud.view.ViewUser;

public class RouterUser {

    public ControllerUser controllerUser;

    public RouterUser() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DaoUser daoUser = new DaoUser(databaseConnection);
        ServiceUser serviceUser = new ServiceUser(daoUser);
        ViewUser viewUser = new ViewUser();
        this.controllerUser = new ControllerUser(serviceUser, viewUser);
    }

    public boolean addUser(String lastName, String firstName, String email) {
        ModelUser modelUser = new ModelUser(lastName,firstName, email);
        return this.controllerUser.addUser(modelUser);
    }

    public boolean deleteUser(String email) {
        ModelUser modelUser = new ModelUser();
        return this.controllerUser.deleteUser(modelUser, email);
    }

    public boolean updateUser(UpdateUserParams updateUserParams) {
        ModelUser modelUser = new ModelUser();
        return this.controllerUser.updateUser(modelUser, updateUserParams);
    }

    public void getAllUsers() {
        this.controllerUser.getAllUsers ();
    }

    public void getUsersByFirstName(String name) {
        this.controllerUser.getUsersByFirstName(name);
    }
}
