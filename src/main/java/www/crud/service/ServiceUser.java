package www.crud.service;
import www.crud.dao.DaoUser;
import www.crud.model.ModelUser;
import www.crud.param.UpdateUserParams;

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
        catch (Exception e) {  // Catch more specific exceptions as needed
            System.err.println("SQL Error: " + e.getMessage());
            // Return false or handle differently if an error occurs
        }
        return false;
    }

    public boolean isUserExistingByEmail(String email) {
        try  {
            return daoUser.isUserExistingByEmail(email);
        }
        catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public boolean loadUserByEmail(ModelUser modelUser, String email) {
        try {
            return daoUser.loadUserByEmail(modelUser, email);
        }
        catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
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
            boolean isUserLoaded = this.loadUserByEmail(modelUser, email);
            if(!isUserLoaded) {
                System.out.println("Le chargement de l'utilisateur a échoué.");
                return false;
            }
            return daoUser.deleteUser(modelUser, email);
        }
        catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateEmail(ModelUser modelUser, UpdateUserParams params) {
        try {
            boolean isUserExist = this.isUserExistingByEmail(params.getOldEmail());
            if(!isUserExist) {
                System.out.println("Aucun utilisateur n'existe à cet email.");
                return false;
            }
            boolean isUserLoaded = this.loadUserByEmail(modelUser, params.getOldEmail());
            if(!isUserLoaded) {
                System.out.println("Le chargement de l'utilisateur a échoué.");
                return false;
            }
            return daoUser.updateEmail(modelUser, params);
        }
        catch (Exception e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public List<ModelUser> getAllUsers() {
        return this.daoUser.getAllUsers();
    }

    public List<ModelUser> getUsersByFirstName(String name) {
        return this.daoUser.getUsersByFirstName(name);
    }
}
