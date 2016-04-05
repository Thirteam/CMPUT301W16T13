package cmput301.textbookhub;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_Main;
import cmput301.textbookhub.Views.Fragment_Search;

/**
 * Created by runqiwang on 16-03-30.
 */
public class SearchTest extends ActivityInstrumentationTestCase2 {
    public SearchTest(){super(Activity_Main.class);}

    public void testSearchEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Main am = (Activity_Main) getActivity();

        EditText SearchET = (EditText) am.findViewById(R.id.et_search);
        ViewAsserts.assertOnScreen(am.getWindow().getDecorView(), SearchET);
    }

    public void testSearchBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Main am = (Activity_Main) getActivity();

        Button SearchBtn = (Button) am.findViewById(R.id.btn_search);
        ViewAsserts.assertOnScreen(am.getWindow().getDecorView(), SearchBtn);
    }

    public void testSearchListView(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Main am = (Activity_Main) getActivity();

        ListView SearchLV = (ListView) am.findViewById(R.id.lv_search_result);
        ViewAsserts.assertOnScreen(am.getWindow().getDecorView(), SearchLV);

    }

}

