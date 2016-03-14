package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-10.
 */
public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest(){
        super(cmput301.textbookhub.Models.Bid.class);
    }

    public void testBid(){
        BookShelf bs1 = new BookShelf();
        BookShelf bs2 = new BookShelf();
        User user1 = new User("fred","123",bs1);
        User user2 = new User("runqi","1234",bs2);
        TextBook tb = new TextBook(user1,"BOOK1");
        Bid bid = new Bid(100.00, user2, tb);

        assertEquals("bid amount is correct",100.00,bid.getAmount());
        assertEquals("bidder is correct","runqi",bid.getBidder().getName());
        assertEquals("textbook is correct",tb, bid.getTextBook());
    }
}
