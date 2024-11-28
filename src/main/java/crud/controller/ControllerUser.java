package crud.controller;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;
import crud.ServiceUser;
import crud.entity.EntityUser;
import crud.view.ViewUser;

import java.util.List;

public class ControllerUser {

    private final ServiceUser serviceUser;
    private final ViewUser viewUser;

    public ControllerUser(ServiceUser serviceUser, ViewUser viewUser) {
        this.serviceUser = serviceUser;
        this.viewUser = viewUser;
    }

    public boolean addUser(EntityUser entityUser) {
        boolean isSuccessRequest = this.serviceUser.addUser(entityUser);
        if(!isSuccessRequest) return false;

        String message = String.format("L'ajout de l'utilisateur a été effectué avec succès : " +
                        "ID:%d Prénom: %s, Nom: %s, Email: %s",
        entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());
        this.viewUser.renderMessageConsole(message);

        return true;
    }

    public void deleteUser(EntityUser entityUser, String email) {
        boolean isSuccessRequest = this.serviceUser.deleteUser(entityUser, email);
        if(!isSuccessRequest) return;

        String message = String.format("La suppression de l'utilisateur a été effectué avec succès : " +
                        "ID:%d Prénom: %s, Nom: %s, Email: %s",
             entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());
        this.viewUser.renderMessageConsole(message);
    }

    public void updateUser(EntityUser entityUser, UpdateUserParams params) {
        boolean isSuccessRequest = this.serviceUser.updateUser(entityUser, params);
        if(!isSuccessRequest) return;

        String message = String.format("La mise à jour de l'email de l'utilisateur a été effectué avec succès : " +
                        "ID:%d Nouvel email: %s Ancien email: %s",
                entityUser.getId(), entityUser.getEmail(), params.getNewEmail());
        this.viewUser.renderMessageConsole(message);
    }

    public void getAllUsers() {
        List<EntityUser> users = this.serviceUser.getAllUsers();
        for (EntityUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }

    public void getUsersBySearch (SearchUserByParams searchUserByParams) {
        List<EntityUser> users = this.serviceUser.getUsersBySearch(searchUserByParams);
        if(users == null) return;
        for (EntityUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }

    public void deleteAllUsers() {
        boolean isUsersDeleted = this.serviceUser.deleteAllUsers();
        if(isUsersDeleted) {
            this.viewUser.renderMessageConsole("Les utilisateurs ont été réinitialisés");
        }
        this.viewUser.renderMessageConsole( "Les utilisateurs n'ont pas été réinitialisés");
    }
}
