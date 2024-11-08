package crud.service;
import crud.dao.DaoUser;
import crud.model.ModelUser;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;

import java.util.List;

public class ServiceUser {
    private final DaoUser daoUser;

    public ServiceUser(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    public boolean addUser(ModelUser modelUser) {
        try {
            boolean isUserExist = this.isUserExistingByEmail(modelUser.getEmail());
            if(isUserExist) {
                System.out.println("Cet email existe déjà.");
                return false;
            }
            return daoUser.addUser(modelUser);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean isUserExistingByEmail(String email) {
        try  {
            return daoUser.isUserExistingByEmail(email);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }
    
    public Integer getUserIdByEmail(String email) {
        try {
            return daoUser.getUserIdByEmail(email);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    public boolean loadModelUserByEmail(ModelUser modelUser, String email) {
        try {
            // Cause Model not loaded before, we need user ID sql here
            int userId = this.getUserIdByEmail(email);
            if(userId < 1) return false;

            return daoUser.loadModelUserByEmail(modelUser, userId);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(ModelUser modelUser, String email) {
        try {
            boolean isUserExist = this.isUserExistingByEmail(email);
            if(!isUserExist) {
                System.out.println("Aucun utilisateur n'existe à cet email.");
                return false;
            }
            boolean isUserLoaded = this.loadModelUserByEmail(modelUser, email);
            if(!isUserLoaded) {
                System.out.println("Le chargement de l'utilisateur a échoué.");
                return false;
            }
            return daoUser.deleteUser(modelUser);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateEmail(ModelUser modelUser, UpdateUserParams params) {
        try {
            boolean isUserExist = this.isUserExistingByEmail(params.getCurrentEmail());
            if(!isUserExist) {
                System.out.println("Aucun utilisateur n'existe à cet email.");
                return false;
            }
            boolean isUserLoaded = this.loadModelUserByEmail(modelUser, params.getCurrentEmail());
            if(!isUserLoaded) {
                System.out.println("Le chargement de l'utilisateur a échoué.");
                return false;
            }
            return daoUser.updateEmail(modelUser, params);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public List<ModelUser> getAllUsers() {
        return this.daoUser.getAllUsers();
    }

    public List<ModelUser> getUsersBySearch(SearchUserByParams searchUserByParams) {
        return this.daoUser.getUsersBySearch(searchUserByParams);
    }

    public boolean deleteAllUsers() {
        return this.daoUser.deleteAllUsers();
    }
}
