package cmput301.textbookhub;


import android.test.ActivityInstrumentationTestCase2;

import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-11.
 */
public class TextBookTest extends ActivityInstrumentationTestCase2{
    public TextBookTest(){super(cmput301.textbookhub.Models.TextBook.class);}

    public void testMakeBook(){
        BookShelf bs = new BookShelf();
        User user = new User("runqi","1234",bs);
        TextBook book = new TextBook(user,"book");


        assertEquals("Book is made successfully", "runqi", book.getOwner().getName());
    }
}
