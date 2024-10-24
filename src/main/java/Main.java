import www.crud.param.UpdateUserParams;
import www.crud.router.RouterUser;

public class Main {
    public static void main(String[] args) {

        // je pourrai très bien créer une class simulation pour mettre tout ça, pour simuler des requêttes
        RouterUser routerUser = new RouterUser();
        boolean isSuccessAction = routerUser.addUser("Jawed", "Bwetta", "maroc3@gmail.com");
        boolean isUserDeleted = routerUser.deleteUser("maroc@gmail.com");

        routerUser.getAllUsers();
        //routerUser.getUsersByFirstName("Ya");

        UpdateUserParams params = new UpdateUserParams.Builder()
                .withFirstName("eezdazdza")
                .withOldEmail("maroc3@gmail.com")
                .withNewEmail("Jetest@gmail.com")
                .build();
        routerUser.updateUser(params);
    }
}