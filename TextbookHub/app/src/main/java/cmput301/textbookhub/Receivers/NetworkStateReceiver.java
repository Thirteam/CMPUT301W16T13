package cmput301.textbookhub.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Fred on 2016/3/31.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private NetworkStateManager manager = NetworkStateManager.getInstance();



    @Override
    public void onReceive(Context context, Intent intent) {
        manager.onNetworkStateChange(context);
    }
}
