package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;

import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-11.
 */
public class BookShelfTest extends ActivityInstrumentationTestCase2 {
    public BookShelfTest(){super(cmput301.textbookhub.Models.BookShelf.class);}

    public void testAddPersonalBook(){
        BookShelf bs = new BookShelf();
        User user = new User("runqi","1234",bs);
        TextBook pb = new TextBook(user,"book");

        user.getBookShelf().addNewTextBook(pb);

        assertEquals("Adding personal book to shelf is done", pb, bs.getAllBooks().get(0));
    }

}
