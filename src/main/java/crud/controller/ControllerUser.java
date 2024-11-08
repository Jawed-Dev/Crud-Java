package crud.controller;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;
import crud.service.ServiceUser;
import crud.model.ModelUser;
import crud.view.ViewUser;

import java.util.List;

public class ControllerUser {

    private final ServiceUser serviceUser;
    private final ViewUser viewUser;

    public ControllerUser(ServiceUser serviceUser, ViewUser viewUser) {
        this.serviceUser = serviceUser;
        this.viewUser = viewUser;
    }

    public boolean addUser(ModelUser modelUser) {
        boolean isUserAdded = this.serviceUser.addUser(modelUser);
        if(!isUserAdded) {
            this.viewUser.renderMessageConsole("L'ajout de l'utilisateur n'a pas été effectué.");
            return false;
        }

        String message = String.format("L'ajout de l'utilisateur a été effectué avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s",
            modelUser.getId(), modelUser.getFirstName(), modelUser.getLastName(), modelUser.getEmail());
        this.viewUser.renderMessageConsole(message);
        return true;
    }

    public boolean deleteUser(ModelUser modelUser, String email) {
        boolean isUserDeleted = this.serviceUser.deleteUser(modelUser, email);
        if(!isUserDeleted) {
            this.viewUser.renderMessageConsole("La suppression de l'utilisateur a échoué.");
            return false;
        }

        String message = String.format("La suppression de l'utilisateur a été effectué avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s",
             modelUser.getId(), modelUser.getFirstName(), modelUser.getLastName(), modelUser.getEmail());
        this.viewUser.renderMessageConsole(message);
        return true;
    }

    public boolean updateUser(ModelUser modelUser, UpdateUserParams params) {
        boolean isEmailUpdated = this.serviceUser.updateEmail(modelUser, params);
        if(!isEmailUpdated) {
            this.viewUser.renderMessageConsole("La mise à jour de l'email de l'utilisateur a échoué.");
            return false;
        }

        String message = String.format("La mise à jour de l'email de l'utilisateur a été effectué avec succès : ID:%d Nouvel email: %s Ancien email: %s",
            modelUser.getId(), modelUser.getEmail(), params.getNewEmail());
        this.viewUser.renderMessageConsole(message);
        return true;
    }

    public void getAllUsers() {
        List<ModelUser> users = this.serviceUser.getAllUsers();
        for (ModelUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }

    public void getUsersBySearch (SearchUserByParams searchUserByParams) {
        List<ModelUser> users = this.serviceUser.getUsersBySearch(searchUserByParams);
        for (ModelUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }

    public boolean deleteAllUsers() {
        boolean isUsersDeleted = this.serviceUser.deleteAllUsers();
        String message = (isUsersDeleted) ? "Les utilisateurs ont été réinitialisés" : "Les utilisateurs n'ont pas été réinitialisés";
        this.viewUser.renderMessageConsole(message);
        return isUsersDeleted;
    }
}
