package cmput301.textbookhub.Receivers;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

/**
 * <code>NetworkStateManager</code> is used to manage/monitor offline and online connectivity.
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/31
 * @see NetworkStateObserver
 * @see NetworkStateReceiver
 *
 * Created by Fred on 2016/3/31.
 */
public class NetworkStateManager{

    static NetworkStateManager instance;

    private ArrayList<NetworkStateObserver> controllerObservers = new ArrayList<>();
    private ArrayList<NetworkStateObserver> viewObservers = new ArrayList<>();
    private boolean isAppActive = true;

    private NetworkStateManager(){}

    /**
     * <code>addControllerObserver</code> adds a controller observer to <code>controllerObservers</code>
     *
     * @param observer the <code>NetworkStateObserver</code> to add
     */
    public void addControllerObserver(NetworkStateObserver observer){
        if(!controllerObservers.contains(observer))
            this.controllerObservers.add(observer);
    }

    /**
     * <code>addViewObserver</code> adds a view observer to <code>viewObservers</code>
     *
     * @param observer the <code>NetworkStateObserver</code> to add
     */
    public void addViewObserver(NetworkStateObserver observer){
        if(!viewObservers.contains(observer))
            this.viewObservers.add(observer);
    }

    /**
     * <code>removeViewObserver</code> removes a view observer from <code>viewObservers</code>
     *
     * @param observer the observer to remove
     */
    public void removeViewObserver(NetworkStateObserver observer){
        this.viewObservers.remove(observer);
    }

    /**
     * <code>onNetworkStateChange</code> checks for connection and executes actions accordingly
     *
     * @param context the context
     */
    public void onNetworkStateChange(Context context) {
        if(isAppActive) {
            if (isInternetConnected(context)) {
                for (NetworkStateObserver o : controllerObservers)
                    o.onInternetConnect();
                for (NetworkStateObserver o : viewObservers)
                    o.onInternetConnect();
            } else {
                for (NetworkStateObserver o : controllerObservers)
                    o.onInternetDisconnect();
                for (NetworkStateObserver o : viewObservers)
                    o.onInternetDisconnect();
            }
        }
    }

    /**
     * <code>isInternetConnected</code> checks for an internet network connection
     *
     * @param context the context
     * @return <code>true</code> if connected to network, <code>false</code> otherwise
     */
    public boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }

    /**
     * <code>getInstance</code> gets the instance of the <code>NetworkStateManager</code>
     *
     * @return a new <code>NetworkStateManager</code> if one does not exist.
     */
    public static NetworkStateManager getInstance(){
        if(instance == null)
            instance = new NetworkStateManager();
        return instance;
    }

    public void setAppActive(){
        this.isAppActive = true;
    }

    public void setAppInactive(){
        this.isAppActive = false;
    }
}
