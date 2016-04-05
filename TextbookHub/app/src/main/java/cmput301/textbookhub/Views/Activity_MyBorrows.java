package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MyBorrowsActivityController;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateManager;
import cmput301.textbookhub.Receivers.NetworkStateObserver;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyBorrows extends AppCompatActivity implements BaseView, NetworkStateObserver{

    private ListView lv_my_borrows;
    private LinearLayout layout_borrows_hint;
    private Context context;

    private MyBorrowsActivityController activityController;
    private AppUserController userController;
    private MyBorrowedListAdapter adapter;

    public static final int REQUEST_DID_RETURN_BOOK = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);

        this.activityController = (MyBorrowsActivityController) ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_MY_BORROWS, this);
        this.userController = AppUserController.getInstance();

        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_borrows));
        lv_my_borrows = (ListView) findViewById(R.id.lv_borrowed);
        lv_my_borrows.setVisibility(View.GONE);
        //TODO:modify adapter input data
        adapter = new MyBorrowedListAdapter(this.context, R.layout.adapter_book_borrowed, activityController.getBooksIBorrowed(userController.getAppUser().getName()));
        lv_my_borrows.setAdapter(adapter);
        layout_borrows_hint = (LinearLayout) findViewById(R.id.layout_borrows_hint);
        lv_my_borrows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!userController.hasInternetAccess(context)) {
                    return;
                }
                if(!activityController.isOkToQuery(((MyBorrowedListAdapter) lv_my_borrows.getAdapter()).getItem(position).getID())){
                    activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.wait_update));
                    return;
                }
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((MyBorrowedListAdapter) lv_my_borrows.getAdapter()).getItem(position).getID());
                i.putExtra(Activity_ViewBook.INTENT_EXTRAS_KEY_BUNDLE, b);
                startActivityForResult(i, REQUEST_DID_RETURN_BOOK);
            }
        });
        adapter = new MyBorrowedListAdapter(this.context, R.layout.adapter_book_borrowed, activityController.getBooksIBorrowed(userController.getAppUser().getName()));
        this.lv_my_borrows.setAdapter(this.adapter);
        adapter.notifyDataSetChanged();
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
        if(this.lv_my_borrows.getAdapter().getCount() == 0) {
            this.layout_borrows_hint.setVisibility(View.VISIBLE);
            this.lv_my_borrows.setVisibility(View.GONE);
        }else{
            this.layout_borrows_hint.setVisibility(View.GONE);
            this.lv_my_borrows.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DID_RETURN_BOOK && resultCode == RESULT_OK){
            String book_id = data.getStringExtra(Activity_ViewBook.ACTIVITY_RESULT_KEY_BOOK_ID);
            if(book_id!=null && data.hasExtra(Activity_ViewBook.ACTIVITY_RESULT_KEY_BOOK_RETURNED)){
                refreshListViewData(book_id);
            }else{
                Log.i("ON RESULT FAILED", "NO ID FOUND");
            }
        }
    }

    private void refreshListViewData(String id){
        this.adapter.removeByID(id);
        this.adapter.notifyDataSetChanged();
        updateView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
        BaseApplication.activityResumed();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseApplication.activityPaused();
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
