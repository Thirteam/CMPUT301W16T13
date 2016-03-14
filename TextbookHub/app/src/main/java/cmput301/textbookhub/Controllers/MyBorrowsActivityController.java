package cmput301.textbookhub.Controllers;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
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
