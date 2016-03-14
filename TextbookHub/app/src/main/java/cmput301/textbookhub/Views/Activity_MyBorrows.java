package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.MyBorrowsActivityController;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.R;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class Activity_MyBorrows extends AppCompatActivity implements BaseView{

    private ListView lv_my_borrows;
    private LinearLayout layout_borrows_hint;
    private Context context;

    private MyBorrowsActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);

        this.controller = (MyBorrowsActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_MY_BORROWS, this, ((BaseApplication)getApplication()).getAppUsername());

        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_borrows));
        lv_my_borrows = (ListView) findViewById(R.id.lv_borrowed);
        lv_my_borrows.setVisibility(View.GONE);
        //TODO:modify adapter input data
        lv_my_borrows.setAdapter( new BorrowedListAdapter(this.context, R.layout.adapter_book_borrowed, new ArrayList<TextBook>()));
        layout_borrows_hint = (LinearLayout) findViewById(R.id.layout_borrows_hint);
        lv_my_borrows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((BorrowedListAdapter) lv_my_borrows.getAdapter()).getItem(position).getID());
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
