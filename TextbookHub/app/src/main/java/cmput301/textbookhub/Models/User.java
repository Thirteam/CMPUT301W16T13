package cmput301.textbookhub.Models;

import cmput301.textbookhub.NamedItem;
import cmput301.textbookhub.UniqueItem;

/**
 * Created by Fred on 2016/3/8.
 */
public class User implements NamedItem, UniqueItem<String>{

    public User() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }
}
