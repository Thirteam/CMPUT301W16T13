package cmput301.textbookhub;

import android.app.Application;

import cmput301.textbookhub.Controllers.BaseController;
import cmput301.textbookhub.Controllers.ControllerFactory;

/**
 * Created by Fred on 2016/3/1.
 */
public class BaseApplication extends Application {

    ControllerFactory controllerFactory = ControllerFactory.getInstance();

    public ControllerFactory getControllerFactory(){
        return this.controllerFactory;
    }

}
