package cmput301.textbookhub.Receivers;

/**
 * <code>NetworkStateObserver</code> is used to check for internet connection/disconnection.
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/31
 * @see NetworkStateManager
 * @see NetworkStateReceiver
 *
 * Created by Fred on 2016/3/31.
 */
public interface NetworkStateObserver {

    void onInternetConnect();

    void onInternetDisconnect();

}
