package cmput301.textbookhub.Models;

/**
 * Created by Fred on 2016/3/10.
 */
public enum DataBundleLabel{
    BID("BID"),
    TEXTBOOK("TEXTBOOK"),
    USER("USER");

    private final String name;

    DataBundleLabel(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
