
package cmput301.textbookhub;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_MyInventory;

/**
 * Created by runqiwang on 16-03-13.
 */
public class MyInventoryTest extends ActivityInstrumentationTestCase2 {
    public MyInventoryTest(){super(Activity_MyInventory.class);}

    public void testAddNewBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_MyInventory ami = (Activity_MyInventory) getActivity();

        Button AddNewBtn = (Button) ami.findViewById(R.id.button_new);
        ViewAsserts.assertOnScreen(ami.getWindow().getDecorView(), AddNewBtn);
    }

    public void testMyInventoryListView(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_MyInventory ami = (Activity_MyInventory) getActivity();

        ListView MyInventoryLV = (ListView) ami.findViewById(R.id.lv_inventory);
        ViewAsserts.assertOnScreen(ami.getWindow().getDecorView(), MyInventoryLV);
    }
}
