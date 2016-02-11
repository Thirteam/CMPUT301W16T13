package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;

/**
 * Created by Fred on 2016/2/10.
 */
public class BidTest extends ActivityInstrumentationTestCase2 {

    public BidTest(){
        super(BidTest.class);
    }

    public void testPlaceBid(){
        User u = new User();
        Bid d = new Bid(30, u);
        Textbook t = new Textbook("Math");
        t.setCurrentBid(d);
        assertEquals(t.getCurrentBid(), 30);
    }

    @UiThreadTest
    public void testPendingBid(){
        Activity_PendingBids ac = (Activity_PendingBids) getActivity();
        ViewAsserts.assertOnScreen(ac.getWindow().getDecorView(), ac.findViewById(R.id.lv_pending_bids));
    }

    public void testNewBidNotify(){
        User user = new User();
        Textbook b = new Textbook("Math");
        user.addNewItem(b);
        User borrower = new User();
        b.setCurrentBid(new Bid(20, borrower));
        assertTrue(user.checkNewBids());
    }
}
