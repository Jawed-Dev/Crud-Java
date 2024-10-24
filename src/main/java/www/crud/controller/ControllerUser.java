package www.crud.controller;
import www.crud.param.UpdateUserParams;
import www.crud.service.ServiceUser;
import www.crud.model.ModelUser;
import www.crud.view.ViewUser;

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

    public void getUsersByFirstName (String name) {
        List<ModelUser> users = this.serviceUser.getUsersByFirstName(name);
        for (ModelUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }
}
