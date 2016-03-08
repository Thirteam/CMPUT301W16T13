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
public class Activity_MyBorrows extends AppCompatActivity {

    ListView lv_my_borrows;
    LinearLayout layout_borrows_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_my_borrows));
        lv_my_borrows = (ListView) findViewById(R.id.lv_borrowed);
        lv_my_borrows.setVisibility(View.GONE);
        layout_borrows_hint = (LinearLayout) findViewById(R.id.layout_borrows_hint);
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
