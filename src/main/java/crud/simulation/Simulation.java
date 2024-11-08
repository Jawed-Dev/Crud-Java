package crud.simulation;
import crud.param.SearchUserByParams;
import crud.param.UpdateUserParams;
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
        String currentEmail = "jawed.sim1@gmail.com";
        String newEmail = "jawed.sim1.update@gmail.com";
        this.routerUser.addUser("Jawed", "bouta", currentEmail);

        UpdateUserParams params = new UpdateUserParams.Builder()
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
        String currentEmail = "jawed.sim2@gmail.com";
        this.routerUser.addUser("Jawed", "bouta", currentEmail);
        routerUser.getAllUsers();
    }

    /**
     * Simulation 3: Add an user and delete this user.
     */
    public void simulation3() {
        String currentEmail = "jawed.sim3@gmail.com";
        this.routerUser.addUser("Jawed", "bouta", currentEmail);
        routerUser.deleteUser(currentEmail);
    }

    /**
     * Simulation 4: Get list of users by search
     */
    public void simulation4() {
        SearchUserByParams searchUserByParams = new SearchUserByParams.Builder()
                .withEmail("jawed")
                .withFirstName("Coucou")
                .build();
        this.routerUser.getUsersBySearch(searchUserByParams);
    }

    public boolean clearSimulations() {
        return this.routerUser.deleteAllUsers();
    }
}
