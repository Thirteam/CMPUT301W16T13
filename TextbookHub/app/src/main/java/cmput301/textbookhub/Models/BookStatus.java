package cmput301.textbookhub.Models;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public enum BookStatus {

    AVAILABLE("Available"),
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
