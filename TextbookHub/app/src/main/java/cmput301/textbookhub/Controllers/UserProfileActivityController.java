package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class UserProfileActivityController extends BaseController {

    private static UserProfileActivityController instance;

    private UserProfileActivityController() {
    }

    public static UserProfileActivityController getInstance() {
        if (instance == null)
            instance = new UserProfileActivityController();
        return instance;
    }

}
