package cmput301.textbookhub.Controllers;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class MyBidsActivityController extends BaseController {

    private static MyBidsActivityController instance;

    private MyBidsActivityController() {
    }

    public static MyBidsActivityController getInstance(String username){
        if(instance == null) {
            instance = new MyBidsActivityController();

        }
        instance.initAppUser(username);
        return instance;
    }

}
