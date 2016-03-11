package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyInventoryActivityController extends BaseController {

    private static MyInventoryActivityController instance;

    private MyInventoryActivityController() {
    }

    public static MyInventoryActivityController getInstance(){
        if(instance == null)
            instance = new MyInventoryActivityController();
        return instance;
    }

}
