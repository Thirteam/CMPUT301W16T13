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

import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyBorrows extends AppCompatActivity {

    ListView lv_my_borrows;
    LinearLayout layout_borrows_hint;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);
        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_borrows));
        lv_my_borrows = (ListView) findViewById(R.id.lv_borrowed);
        lv_my_borrows.setVisibility(View.GONE);
        //TODO:modify adapter input data
        lv_my_borrows.setAdapter( new BookListAdapter(this.context, R.layout.adapter_book_borrowed, new ArrayList<TextBook>()));
        layout_borrows_hint = (LinearLayout) findViewById(R.id.layout_borrows_hint);
        lv_my_borrows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((BookListAdapter) lv_my_borrows.getAdapter()).getItem(position).getID());
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

}
