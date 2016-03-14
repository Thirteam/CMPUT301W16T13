package cmput301.textbookhub.Models;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public interface UniqueItem<T> {

    T getID();

    void setID(T id);
}
