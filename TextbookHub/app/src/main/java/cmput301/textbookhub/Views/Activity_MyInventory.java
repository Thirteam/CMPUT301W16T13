package cmput301.textbookhub.Views;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.MyInventoryActivityController;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyInventory extends AppCompatActivity implements BaseView{
    private ListView lv_my_books;
    private LinearLayout layout_inventory_hint;
    private Button btn_new;
    private Context context;
    private ArrayList inventoryList;

    private MyInventoryActivityController controller;

    private InventoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);

        this.controller = (MyInventoryActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_MY_INVENTORY, this, ((BaseApplication)getApplication()).getAppUsername());

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lv_my_books = (ListView) findViewById(R.id.lv_inventory);
        inventoryList = this.controller.getAllBooksList();
        this.adapter = new InventoryListAdapter(this.context, R.layout.adapter_book_inventory, inventoryList);
        lv_my_books.setAdapter(this.adapter);
        layout_inventory_hint = (LinearLayout) findViewById(R.id.layout_inventory_hint);
        btn_new = (Button) findViewById(R.id.button_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_EditBook.class);
                startActivity(intent);
            }
        });

        lv_my_books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((InventoryListAdapter) lv_my_books.getAdapter()).getItem(position).getID());
                i.putExtra(Activity_ViewBook.INTENT_EXTRAS_KEY_BUNDLE, b);
                startActivity(i);
            }
        });
        this.updateView();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.spinner_list_content_type);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_row, R.id.custom_spinner_text, getResources().getStringArray(R.array.inventory_list_content_array)){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                return view;

            }
        };
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.button_indigo));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Change the content of the listview when spinner selection changes
                int index = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Must select one
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void updateView(){
        adapter.notifyDataSetChanged();
        this.lv_my_books.setAdapter(adapter);
        if (adapter.getCount()==0){
            this.lv_my_books.setVisibility(View.GONE);
            this.layout_inventory_hint.setVisibility(View.VISIBLE);
        }else{
            this.lv_my_books.setVisibility(View.VISIBLE);
            this.layout_inventory_hint.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateView();
    }
}
