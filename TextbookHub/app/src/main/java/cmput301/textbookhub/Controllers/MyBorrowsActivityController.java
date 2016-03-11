package cmput301.textbookhub.Controllers;

import android.content.Context;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyBorrowsActivityController extends BaseController {

    private static MyBorrowsActivityController instance;

    private MyBorrowsActivityController() {
    }

    public static MyBorrowsActivityController getInstance(){
        if(instance == null)
            instance = new MyBorrowsActivityController();
        return instance;
    }

}
