package cmput301.textbookhub.Controllers;

import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/8.
 */
public interface BaseObservable {

    void addObserverView(BaseView v);

    void notifyObserverView();

}
