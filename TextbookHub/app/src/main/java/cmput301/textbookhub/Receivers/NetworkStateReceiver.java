package cmput301.textbookhub.Receivers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * <code>NetworkStateReceiver</code>
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/31
 * @see NetworkStateManager
 * @see NetworkStateObserver
 *
 * Created by Fred on 2016/3/31.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private NetworkStateManager manager = NetworkStateManager.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
            manager.onNetworkStateChange(context);
    }

}
