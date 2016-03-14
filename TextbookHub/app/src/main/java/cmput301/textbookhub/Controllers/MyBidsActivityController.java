package cmput301.textbookhub.Controllers;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyBidsActivityController extends BaseController {

    private static MyBidsActivityController instance;

    private MyBidsActivityController() {
    }

    public static MyBidsActivityController getInstance(String username){
        if(instance == null) {
            instance = new MyBidsActivityController();
            instance.initAppUser(username);
        }
        return instance;
    }

}
