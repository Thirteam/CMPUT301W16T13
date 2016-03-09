package cmput301.textbookhub.Models;

import cmput301.textbookhub.NamedItem;
import cmput301.textbookhub.UniqueItem;

/**
 * Created by Fred on 2016/3/8.
 */
public class TextBook implements NamedItem, UniqueItem<String>{

    private String id;
    private String bookName;
    private String edition;
    private String comments;

    private User owner;
    private User borrower;

    private BookStatus bookStatus = BookStatus.PENDING;

    

    @Override
    public String getName() {
        return this.bookName;
    }

    @Override
    public String getID() {
        return this.id;
    }

}
