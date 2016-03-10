package cmput301.textbookhub.Models;

/**
 * Created by Fred on 2016/3/8.
 */
public enum SyncStatus {

    //TODO: don't know if this enum is required

    SYNCED("SYNCED"),
    OBSOLETE("OBSOLETE");

    private final String name;

    SyncStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
