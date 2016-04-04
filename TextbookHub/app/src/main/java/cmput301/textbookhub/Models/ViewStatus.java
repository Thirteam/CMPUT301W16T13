package cmput301.textbookhub.Models;

/**
 * <code>ViewStatus</code>
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/08
 *
 * Created by Fred on 2016/3/8.
 */
public enum ViewStatus {

    //TODO: don't know if this enum is required

    HAS_NEW_BID("HAS_NEW_BID"),
    NO_NEW_BID("NO_NEW_BID");

    private final String name;

    ViewStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
