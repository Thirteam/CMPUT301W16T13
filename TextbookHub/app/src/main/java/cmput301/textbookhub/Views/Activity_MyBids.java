package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MyBidsActivityController;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateManager;
import cmput301.textbookhub.Receivers.NetworkStateObserver;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyBids extends AppCompatActivity implements BaseView, NetworkStateObserver{

    private ListView lv_my_bids;
    private LinearLayout layout_bids_hint;
    private Context context;

    private MyBidsActivityController activityController;
    private AppUserController userController;
    private MyBidListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);

        this.activityController = (MyBidsActivityController) ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_MY_BIDS, this);
        this.userController = AppUserController.getInstance();

        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_bids));
        lv_my_bids = (ListView) findViewById(R.id.lv_my_bids);
        lv_my_bids.setVisibility(View.GONE);
        layout_bids_hint = (LinearLayout) findViewById(R.id.layout_bids_hint);
        lv_my_bids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userController.hasInternetAccess(context) && !userController.hasServerAccess()) {
                    return;
                } else if (!userController.hasInternetAccess(context)) {
                    return;
                }
                if(!activityController.isOkToQuery(((MyBidListAdapter) lv_my_bids.getAdapter()).getItem(position).getID())){
                    activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.wait_update));
                    return;
                }
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((MyBidListAdapter) lv_my_bids.getAdapter()).getItem(position).getID());
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
        this.adapter = new MyBidListAdapter(this.context, R.layout.adapter_book_bid, activityController.getBooksIBiddedOn(userController.getAppUser().getName()));
        this.lv_my_bids.setAdapter(this.adapter);
        if(this.lv_my_bids.getAdapter().getCount() == 0) {
            this.layout_bids_hint.setVisibility(View.VISIBLE);
            this.lv_my_bids.setVisibility(View.GONE);
        }else{
            this.layout_bids_hint.setVisibility(View.GONE);
            this.lv_my_bids.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkStateManager.getInstance().removeViewObserver(this);
    }

    @Override
    public void onInternetConnect() {}

    @Override
    public void onInternetDisconnect() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.error));
        dialog.setMessage(getResources().getString(R.string.offline_not_available));
        dialog.setPositiveButton(getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


}
