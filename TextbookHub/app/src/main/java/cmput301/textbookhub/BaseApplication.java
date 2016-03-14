package cmput301.textbookhub;

import android.app.Application;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-14
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
