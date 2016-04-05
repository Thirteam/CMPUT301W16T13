package cmput301.textbookhub;

import android.app.Application;

import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Receivers.NetworkStateManager;

/**
 * Created by Fred on 2016/3/13.
 */
public class BaseApplication extends Application {

    AppUserController userController;
    @Override
    public void onCreate() {
        super.onCreate();
        userController = AppUserController.getInstance();
        userController.setAppContext(getApplicationContext());
        userController.loadOfflineCommands();
        NetworkStateManager.getInstance().setAppActive();
    }



}
