package cmput301.textbookhub.Controllers;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyBorrowsActivityController extends BaseController {

    private static MyBorrowsActivityController instance;

    private MyBorrowsActivityController() {
    }

    public static MyBorrowsActivityController getInstance(String username){
        if(instance == null) {
            instance = new MyBorrowsActivityController();

        }
        instance.initAppUser(username);
        return instance;
    }

}
