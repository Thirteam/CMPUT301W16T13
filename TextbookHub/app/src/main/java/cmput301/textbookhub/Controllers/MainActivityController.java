package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class MainActivityController extends BaseController {

    private static MainActivityController instance;

    private MainActivityController() {
    }

    public static MainActivityController getInstance(){
        if(instance == null)
            instance = new MainActivityController();
        return instance;
    }

}
