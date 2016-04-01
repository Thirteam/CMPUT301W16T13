package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.BidList;
import cmput301.textbookhub.Models.BookShelf;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;

/**
 * Created by runqiwang on 16-03-10.
 */
//This text is not finished
public class BidListTest extends ActivityInstrumentationTestCase2 {
    public BidListTest(){
        super(cmput301.textbookhub.Models.BidList.class);
    }

    public void testAddBidList(){

        BookShelf bs1 = new BookShelf();
        BookShelf bs2 = new BookShelf();
        User user1 = new User("fred","123",bs1);
        User user2 = new User("Runqi","1234",bs2);
        Textbook tb = new Textbook(user1,"BOOK1");
        Bid bid = new Bid(100.00, user2, tb);

        BidList bl = new BidList();


        assertEquals("The textbook can be add in bidlist",0,bl.getBids().indexOf(bid)+1);
    }
}
