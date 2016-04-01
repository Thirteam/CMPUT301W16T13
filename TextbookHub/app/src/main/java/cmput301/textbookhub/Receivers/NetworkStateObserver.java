package cmput301.textbookhub.Receivers;

/**
 * Created by Fred on 2016/3/31.
 */
public interface NetworkStateObserver {

    void onInternetConnect();

    void onInternetDisconnect();

}
