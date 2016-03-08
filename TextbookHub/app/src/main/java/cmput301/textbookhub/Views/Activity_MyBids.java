package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyBids extends AppCompatActivity {

    ListView lv_my_bids;
    LinearLayout layout_bids_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_bids));
        lv_my_bids = (ListView) findViewById(R.id.lv_my_bids);
        lv_my_bids.setVisibility(View.GONE);
        layout_bids_hint = (LinearLayout) findViewById(R.id.layout_bids_hint);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
