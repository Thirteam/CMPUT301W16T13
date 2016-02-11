package cmput301.textbookhub;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;

import java.util.List;

/**
 * Created by Fred on 2016/2/10.
 */
public class ElasticSearchTest extends ActivityInstrumentationTestCase2 {

    public ElasticSearchTest(){
        super(ElasticSearchTest.class);
    }

    public void testSearchResult(){
        ElasticSearch<Textbook> s = new ElasticSearch<>();
        List<Textbook> list = s.search("Math");
        if(!list.isEmpty())
            assertTrue(list.get(0).descriptionContains("Math"));
    }

    @UiThreadTest
    public void testResultInfo(){
        Activity_Main ac = (Activity_Main) getActivity();
        ViewAsserts.assertOnScreen(ac.getWindow().getDecorView(), ac.findViewById(R.id.lv_search_result));
    }

}
