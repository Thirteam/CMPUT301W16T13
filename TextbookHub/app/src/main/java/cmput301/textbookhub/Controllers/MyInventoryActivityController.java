package cmput301.textbookhub.Controllers;

import java.util.ArrayList;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.TextBook;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyInventoryActivityController extends BaseController {

    private static MyInventoryActivityController instance;

    private MyInventoryActivityController() {
    }

    public static MyInventoryActivityController getInstance(String username){
        if(instance == null) {
            instance = new MyInventoryActivityController();
        }
        instance.initAppUser(username);
        return instance;
    }

    public ArrayList getAllBooksList(){
        return getAppUser().getBookShelf().getAllBooks();
    }

    public ArrayList getAvailableBooksList(){
        return getAppUser().getBookShelf().getAvailableBooks();
    }

    public ArrayList getBorrowedBooksList(){
        return getAppUser().getBookShelf().getBorrowedBooks();
    }


}
