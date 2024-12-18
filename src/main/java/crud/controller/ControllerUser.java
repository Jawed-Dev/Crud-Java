package crud.controller;
import crud.dto.DtoResponse;
import crud.dto.DtoUser;
import crud.service.ServiceResult;
import crud.service.ServiceUser;
import crud.entity.EntityUser;

import java.util.List;

public class ControllerUser {

    private final ServiceUser serviceUser;
    private static final String MSG_ADD_SUCCESS = "L'ajout de l'utilisateur a été effectué avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s";
    private static final String MSG_DELETE_SUCCESS = "La suppression de l'utilisateur a été effectuée avec succès : ID:%d Prénom: %s, Nom: %s, Email: %s";

    public ControllerUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public DtoResponse<EntityUser> addUser(DtoUser dtoUser) {
        ServiceResult<EntityUser> serviceResult = this.serviceUser.addUser(dtoUser);
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        EntityUser entityUser = serviceResult.getData();
        String message = String.format(MSG_ADD_SUCCESS,
                entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());

        return new DtoResponse<>(true, MSG_ADD_SUCCESS, entityUser);
    }

    public DtoResponse<EntityUser> deleteUser(DtoUser dtoUser) {
        ServiceResult<EntityUser> serviceResult = this.serviceUser.deleteUser(dtoUser);
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        EntityUser entityUser = serviceResult.getData();
        String message = String.format(MSG_DELETE_SUCCESS,
             entityUser.getId(), entityUser.getFirstName(), entityUser.getLastName(), entityUser.getEmail());

        return new DtoResponse<>(true, message, entityUser);
    }

    public DtoResponse<EntityUser> updateUser(DtoUser dtoUser) {
        ServiceResult<EntityUser> serviceResult = this.serviceUser.updateUser(dtoUser);
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        EntityUser entityUser = serviceResult.getData();
        String message = "La mise à jour de l'email de l'utilisateur a été effectué avec succès.";

        return new DtoResponse<>(true, message, entityUser);
    }

    public DtoResponse<List<EntityUser>> getUsersBySearch (DtoUser dtoUser) {
        ServiceResult<List<EntityUser>> serviceResult = this.serviceUser.getUsersBySearch(dtoUser);
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        List<EntityUser> users = serviceResult.getData();
        if(users.isEmpty()) {
            return new DtoResponse<>(false, "ERR_EMPTY_USERS_SEARCH");
        }

        return new DtoResponse<>(true, "La recherche a été effectué avec succès.", users);
    }

    public DtoResponse<List<EntityUser>> getAllUsers() {
        ServiceResult<List<EntityUser>> serviceResult = this.serviceUser.getAllUsers();
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        List<EntityUser> users = serviceResult.getData();
        if(users.isEmpty()) {
            return new DtoResponse<>(false, "ERR_NO_DATA");
        }

        return new DtoResponse<>(true, "Les utilisateurs ont été récupérés avec succès.", users);
    }



    public DtoResponse<Integer> deleteAllUsers() {
        ServiceResult<Integer> serviceResult = this.serviceUser.deleteAllUsers();
        if(!serviceResult.isSuccess()) {
            return new DtoResponse<>(false, serviceResult.getErrorMessage());
        }

        Integer amountUsersDeleted = serviceResult.getData();

        return new DtoResponse<>(true, "Les utilisateurs ont été supprimés avec succès.", amountUsersDeleted);
    }
}
