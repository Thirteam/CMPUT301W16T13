package cmput301.textbookhub;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_MyBorrows;

/**
 * Created by runqiwang on 16-03-13.
 */
public class MyBorrowTest extends ActivityInstrumentationTestCase2 {
    public MyBorrowTest(){super(Activity_MyBorrows.class);}

    public void testLoginBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_MyBorrows amb = (Activity_MyBorrows) getActivity();

        ListView MyBorrowLV = (ListView) amb.findViewById(R.id.lv_borrowed);
        ViewAsserts.assertOnScreen(amb.getWindow().getDecorView(), MyBorrowLV);
    }
}
