package cmput301.textbookhub;

import android.app.Application;

import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Receivers.NetworkStateManager;

/**
 * Created by Fred on 2016/3/13.
 */
public class BaseApplication extends Application {

    AppUserController userController;
    private static boolean activityVisible;
    @Override
    public void onCreate() {
        super.onCreate();
        userController = AppUserController.getInstance();
        userController.setAppContext(getApplicationContext());
        userController.loadOfflineCommands();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

}
