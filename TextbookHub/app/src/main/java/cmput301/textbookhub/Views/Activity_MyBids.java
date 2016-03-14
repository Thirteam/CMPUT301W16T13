package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.MyBidsActivityController;
import cmput301.textbookhub.Models.BidList;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.R;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class Activity_MyBids extends AppCompatActivity implements BaseView{

    private ListView lv_my_bids;
    private LinearLayout layout_bids_hint;
    private Context context;

    private MyBidsActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);

        this.controller = (MyBidsActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_MY_BIDS, this, ((BaseApplication)getApplication()).getAppUsername());

        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_bids));
        lv_my_bids = (ListView) findViewById(R.id.lv_my_bids);
        lv_my_bids.setVisibility(View.GONE);
        layout_bids_hint = (LinearLayout) findViewById(R.id.layout_bids_hint);
        lv_my_bids.setAdapter(new BidListAdapter(this.context, R.layout.adapter_book_bid, new ArrayList<TextBook>()));
        lv_my_bids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((BidListAdapter) lv_my_bids.getAdapter()).getItem(position).getID());
                i.putExtra(Activity_ViewBook.INTENT_EXTRAS_KEY_BUNDLE, b);
                startActivity(i);
            }
        });

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

    @Override
    public void updateView(){

    }
}
