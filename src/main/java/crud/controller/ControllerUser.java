package crud.controller;
import crud.dao.DaoUser;
import crud.dto.DtoResponse;
import crud.dto.DtoUser;
import crud.param.ParamsUser;
import crud.service.ServiceUser;
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

    public DtoResponse<EntityUser> addUser(DtoUser dtoUser) {
        EntityUser entityUser = this.serviceUser.addUser(dtoUser);
        if(entityUser == null) {
            return new DtoResponse<>(false, "Echec de l'ajout de l'utilisateur.");
        }
        String message = String.format("L'ajout de l'utilisateur a été effectué avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s",
            entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());

        return new DtoResponse<>(true, message, entityUser);
    }

    public DtoResponse<EntityUser> deleteUser(DtoUser dtoUser) {
        EntityUser entityUser = this.serviceUser.deleteUser(dtoUser);
        if(entityUser == null) {
            return new DtoResponse<>(false, "Echec de la suppression de l'utilisateur.");
        }
        String message = String.format("La suppression de l'utilisateur a été effectué avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s",
             entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());
        return new DtoResponse<>(true, message, entityUser);
    }

    public DtoResponse<EntityUser> updateUser(DtoUser dtoUser) {
        EntityUser entityUser = this.serviceUser.updateUser(dtoUser);
        if(entityUser == null) {
            return new DtoResponse<>(false, "Echec de la mise à jour de l'utilisateur.");
        }
        String message = "La mise à jour de l'email de l'utilisateur a été effectué avec succès.";
        return new DtoResponse<>(true, message, entityUser);
    }

    public void getAllUsers() {
        List<EntityUser> users = this.serviceUser.getAllUsers();
        for (EntityUser user : users) {
            this.viewUser.renderObjectConsole(user);
        }
    }

    public void getUsersBySearch (DtoUser dtoUser) {
        List<EntityUser> users = this.serviceUser.getUsersBySearch(dtoUser);
        if(users == null) return;
        for (EntityUser user : users) {
            if(user != null) this.viewUser.renderObjectConsole(user);
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
