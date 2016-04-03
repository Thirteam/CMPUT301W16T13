package cmput301.textbookhub.Models;

/**
 * Created by xuefei1 on 3/8/16.
 *
 * A book can be available or borrowed
 */
public enum BookStatus {

    AVAILABLE("Available"),
    BIDDED("Bidded"),
    BORROWED("Borrowed");

    private final String name;

    BookStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }


}
