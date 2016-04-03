package cmput301.textbookhub.Receivers;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

/**
 * Created by Fred on 2016/3/31.
 */
public class NetworkStateManager{

    static NetworkStateManager instance;

    private ArrayList<NetworkStateObserver> controllerObservers = new ArrayList<>();
    private ArrayList<NetworkStateObserver> viewObservers = new ArrayList<>();

    private NetworkStateManager(){}

    public void addControllerObserver(NetworkStateObserver observer){
        if(!controllerObservers.contains(observer))
            this.controllerObservers.add(observer);
    }

    public void addViewObserver(NetworkStateObserver observer){
        if(!viewObservers.contains(observer))
            this.viewObservers.add(observer);
    }

    public void removeViewObserver(NetworkStateObserver observer){
        this.viewObservers.remove(observer);
    }

    public void onNetworkStateChange(Context context) {
        if(isInternetConnected(context)){
            for(NetworkStateObserver o: controllerObservers)
                o.onInternetConnect();
            for(NetworkStateObserver o: viewObservers)
                o.onInternetConnect();
        }else{
            for(NetworkStateObserver o: controllerObservers)
                o.onInternetDisconnect();
            for(NetworkStateObserver o: viewObservers)
                o.onInternetDisconnect();
        }
    }

    public boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }

    public static NetworkStateManager getInstance(){
        if(instance == null)
            instance = new NetworkStateManager();
        return instance;
    }
}
