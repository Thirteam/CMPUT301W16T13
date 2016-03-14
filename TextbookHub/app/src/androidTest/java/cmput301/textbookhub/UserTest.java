package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;

import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-12.
 */
public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){
        super(cmput301.textbookhub.Models.User.class);
    }

    public void testUser(){
        BookShelf bs = new BookShelf();
        User user = new User("runqi","1234",bs);

        assertEquals("ser is made successfully.","runqi",user.getName());
    }
}
