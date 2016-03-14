package cmput301.textbookhub;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;

import java.util.List;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_MyBids;

/**
 * Created by runqiwang on 16-03-13.
 */
public class MyBidTest extends ActivityInstrumentationTestCase2 {
    public MyBidTest(){super(Activity_MyBids.class);}

    public void testMyBidListView(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_MyBids amb = (Activity_MyBids) getActivity();

        ListView MyBidLV = (ListView) amb.findViewById(R.id.lv_my_bids);
        ViewAsserts.assertOnScreen(amb.getWindow().getDecorView(), MyBidLV);
    }
}
