package crud.simulation;
import crud.dto.DtoResponse;
import crud.dto.DtoUser;
import crud.entity.EntityUser;
import crud.param.ParamsUser;
import crud.router.RouterUser;


/**
 * This class permit to simulate requests HTTP
 */
public class Simulation {
    private final RouterUser routerUser;

    public Simulation() {
        this.routerUser = new RouterUser();
    }

    /**
     * Simulation 1 : Add an user and update this user.
     */
    public void simulation1() {
        this.underlineString(1);

        // add user
        String currentEmail = "jawed.sim1@gmail.com";
        DtoUser dtoUser = new DtoUser("Jawed", "Test", currentEmail);
        DtoResponse<EntityUser> dtoResponseAdd = this.routerUser.addUser(dtoUser);
        if(dtoResponseAdd.isSuccess()) {
            System.out.println(dtoResponseAdd.getMessage());
        }

        // update user
        String newEmail = "jawed.sim1.update@gmail.com";
        DtoUser dtoDeleteUser = new DtoUser("Jawed", "Test", currentEmail);
        dtoDeleteUser.setNewEmail(newEmail);
        DtoResponse<EntityUser> dtoResponseUpdate = routerUser.updateUser(dtoDeleteUser);
        if(dtoResponseUpdate.isSuccess()) {
            System.out.println(dtoResponseUpdate.getMessage());
        }
    }

    /**
     * Simulation 2: Add an user, and get list of all users.
     */
    public void simulation2() {
        this.underlineString(2);
        String currentEmail = "jawed.sim2@gmail.com";
        DtoUser dtoUser = new DtoUser("Jawed", "Test", currentEmail);
        DtoResponse<EntityUser> dtoResponseAdd = this.routerUser.addUser(dtoUser);
        if(dtoResponseAdd.isSuccess()) {
            System.out.println(dtoResponseAdd.getMessage());
        }
        routerUser.getAllUsers();
    }

    /**
     * Simulation 3: Add an user and delete this user.
     */
    public void simulation3() {
        this.underlineString(3);

        // add user
        DtoUser dtoUser = new DtoUser("Jawed", "Test", "jawed.sim3@gmail.com");
        DtoResponse<EntityUser> dtoResponseAdd = this.routerUser.addUser(dtoUser);
        if(dtoResponseAdd.isSuccess()) {
            System.out.println(dtoResponseAdd.getMessage());
        }
        // delete user
        DtoResponse<EntityUser> dtoResponseDelete = this.routerUser.deleteUser(dtoUser);
        if(dtoResponseDelete.isSuccess()) {
            System.out.println(dtoResponseDelete.getMessage());
        }
    }

    /**
     * Simulation 4: Get list of users by search
     */
    public void simulation4() {
        this.underlineString(4);
        DtoUser dtoUser = new DtoUser("Jawed", "", "jawed.sim1.update@gmail.com");
        this.routerUser.getUsersBySearch(dtoUser);
    }

    /**
     * Clear simulations
     */
    public void clearSimulations() {
        this.routerUser.deleteAllUsers();
    }

    public void underlineString(int simulationId) {
        System.out.println();
        System.out.println("SIMULATION N° " + simulationId);
        System.out.println("_________________________________");
        System.out.println();
    }
}
