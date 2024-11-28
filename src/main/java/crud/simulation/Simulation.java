package crud.simulation;
import crud.param.ParamsSearchUser;
import crud.param.ParamsUpdateUser;
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
        this.underlineStrig(1);
        String currentEmail = "jawed.sim1@gmail.com";
        String newEmail = "jawed.sim1.update@gmail.com";

        this.routerUser.addUser("Jawed", "bouta", currentEmail);

        ParamsUpdateUser params = new ParamsUpdateUser.Builder()
        .withFirstName("Jawed-new")
        .withCurrentEmail(currentEmail)
        .withNewEmail(newEmail)
        .build();
        routerUser.updateUser(params);
    }

    /**
     * Simulation 2: Add an user, and get list of all users.
     */
    public void simulation2() {
        this.underlineStrig(2);
        String currentEmail = "jawed.sim2@gmail.com";
        this.routerUser.addUser("Jawed", "bouta", currentEmail);
        routerUser.getAllUsers();
    }

    /**
     * Simulation 3: Add an user and delete this user.
     */
    public void simulation3() {
        this.underlineStrig(3);
        String currentEmail = "jawed.sim3@gmail.com";
        this.routerUser.addUser("Jawed", "bouta", currentEmail);
        routerUser.deleteUser(currentEmail);
    }

    /**
     * Simulation 4: Get list of users by search
     */
    public void simulation4() {
        this.underlineStrig(4);
        ParamsSearchUser paramsSearchUser = new ParamsSearchUser.Builder()
                .withEmail("jawed.sim1.update@gmail.com")
                .withFirstName("jawed")
                .build();
        this.routerUser.getUsersBySearch(paramsSearchUser);
    }

    /**
     * Clear simulations
     */
    public void clearSimulations() {
        this.routerUser.deleteAllUsers();
    }

    public void underlineStrig(int simulationId) {
        System.out.println();
        System.out.println("SIMULATION N° " + simulationId);
        System.out.println("_________________________________");
        System.out.println();
    }
}
