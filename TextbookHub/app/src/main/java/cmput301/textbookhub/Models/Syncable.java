package cmput301.textbookhub.Models;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public interface Syncable {

    //TODO: don't know if this interface is required

    SyncStatus getSyncStatus();

    void setSyncStatus(SyncStatus status);

    void onSync();

}
