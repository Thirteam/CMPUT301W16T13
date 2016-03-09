package cmput301.textbookhub.Models;

/**
 * Created by Fred on 2016/3/8.
 */
public interface Syncable {

    SyncStatus getSyncStatus();

    void setSyncStatus(SyncStatus status);

    void onSync();

}
