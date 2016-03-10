package cmput301.textbookhub.Models;

/**
 * Created by xuefei1 on 3/8/16.
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

    public BookStatus findStatus(String name) throws IllegalArgumentException{
        for(BookStatus status : BookStatus.values()){
            if(status.equalsName(name)){
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
