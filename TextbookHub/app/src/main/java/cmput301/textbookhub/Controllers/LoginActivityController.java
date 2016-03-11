package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class    LoginActivityController extends BaseController {

    private static LoginActivityController instance;

    private LoginActivityController() {
    }

    public static LoginActivityController getInstance(){
        if(instance == null)
            instance = new LoginActivityController();
        return instance;
    }

}
