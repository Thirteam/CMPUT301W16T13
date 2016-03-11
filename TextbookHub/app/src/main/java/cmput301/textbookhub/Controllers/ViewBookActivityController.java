package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class ViewBookActivityController extends BaseController {

    private static ViewBookActivityController instance;

    private ViewBookActivityController() {
    }

    public static ViewBookActivityController getInstance(){
        if(instance == null)
            instance = new ViewBookActivityController();
        return instance;
    }

}
