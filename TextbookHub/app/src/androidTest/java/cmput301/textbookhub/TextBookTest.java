package cmput301.textbookhub;


import android.test.ActivityInstrumentationTestCase2;

import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-11.
 */
public class TextbookTest extends ActivityInstrumentationTestCase2{
    public TextbookTest(){super(Textbook.class);}

    public void testMakeBook(){
        BookShelf bs = new BookShelf();
        User user = new User("runqi","1234",bs);
        Textbook book = new Textbook(user,"book");


        assertEquals("Book is made successfully", "runqi", book.getOwner());
    }
}
