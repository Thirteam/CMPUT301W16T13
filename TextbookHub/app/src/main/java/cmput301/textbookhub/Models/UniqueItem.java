package cmput301.textbookhub.Models;

/**
 * Created by xuefei1 on 3/8/16.
 */
public interface UniqueItem<T> {

    T getID();

    void setID(T id);
}
