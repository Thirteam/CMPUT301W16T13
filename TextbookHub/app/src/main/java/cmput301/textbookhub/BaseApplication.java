package cmput301.textbookhub;

import android.app.Application;

/**
 * Created by Fred on 2016/3/13.
 */
public class BaseApplication extends Application {

    private String appUsername = null;

    public String getAppUsername() {
        return appUsername;
    }

    public void setAppUsername(String appUsername) {
        this.appUsername = appUsername;
    }
}
