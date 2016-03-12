package cmput301.textbookhub.Models;

/**
 * Created by xuefei1 on 3/8/16.
 */
public enum BookStatus {

    AVAILABLE("Available"),
    PENDING("Pending"),
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
