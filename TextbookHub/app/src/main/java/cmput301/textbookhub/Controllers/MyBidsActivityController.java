package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyBidsActivityController extends BaseController {

    private static MyBidsActivityController instance;

    private MyBidsActivityController() {
    }

    public static MyBidsActivityController getInstance(){
        if(instance == null)
            instance = new MyBidsActivityController();
        return instance;
    }

}
