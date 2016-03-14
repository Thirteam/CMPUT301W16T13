package cmput301.textbookhub.Models;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
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
