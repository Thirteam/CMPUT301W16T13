package cmput301.textbookhub.Controllers;

/**
 * Created by Fred on 2016/3/10.
 */
public class MainActivityController extends BaseController {

    private static MainActivityController instance;

    private MainActivityController() {
    }

    public static MainActivityController getInstance(String username){
        if(instance == null) {
            instance = new MainActivityController();
            instance.initAppUser(username);
        }
        return instance;
    }

}
