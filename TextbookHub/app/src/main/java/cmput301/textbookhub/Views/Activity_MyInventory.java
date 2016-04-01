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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MyInventoryActivityController;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;

/**
 * Created by Fred on 2016/3/1.
 */
public class Activity_MyInventory extends AppCompatActivity implements BaseView, NetworkStateObserver{

    private ListView lv_my_books;
    private LinearLayout layout_inventory_hint;
    private Button btn_new;
    private Context context;
    private TextView tv_no_conn_hint;
    private ArrayList inventoryList;
    Spinner spinner;

    private MyInventoryActivityController activityController;
    private AppUserController userController;

    private InventoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);

        this.activityController = (MyInventoryActivityController) ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_MY_INVENTORY, this);
        this.userController = AppUserController.getInstance();

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lv_my_books = (ListView) findViewById(R.id.lv_inventory);
        layout_inventory_hint = (LinearLayout) findViewById(R.id.layout_inventory_hint);
        tv_no_conn_hint = (TextView) findViewById(R.id.tv_no_conn_hint);
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

    private void initListViewData(int code){
        if (code == 1) {
            inventoryList = userController.getAvailableBooks();
        } else if (code == 2) {
            inventoryList = userController.getBorrowedPersonalBooks();
        } else {
            inventoryList = userController.getAllPersonalBooks();
        }
        resetAdapter();
    }

    private void resetAdapter(){
        this.adapter = new InventoryListAdapter(this.context, R.layout.adapter_book_inventory, inventoryList);
        this.lv_my_books.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.spinner_list_content_type);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_row, R.id.custom_spinner_text, getResources().getStringArray(R.array.inventory_list_content_array)){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                return view;
            }
        };
        spinner.setAdapter(spinner_adapter);
        spinner.setSelection(0);
        spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.button_indigo));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Change the content of the listview when spinner selection changes
                int index = parent.getSelectedItemPosition();
                initListViewData(index);
                updateView();
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
        if(this.activityController.hasInternetAccess(this)){
            this.tv_no_conn_hint.setVisibility(View.GONE);
            if (this.adapter.getCount() == 0) {
                this.lv_my_books.setVisibility(View.GONE);
                this.layout_inventory_hint.setVisibility(View.VISIBLE);
            } else {
                this.lv_my_books.setVisibility(View.VISIBLE);
                this.layout_inventory_hint.setVisibility(View.GONE);
            }
        } else {
            this.layout_inventory_hint.setVisibility(View.GONE);
            if (this.adapter.getCount() == 0) {
                this.lv_my_books.setVisibility(View.GONE);
                this.tv_no_conn_hint.setVisibility(View.VISIBLE);
            } else {
                this.lv_my_books.setVisibility(View.VISIBLE);
                this.tv_no_conn_hint.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onInternetDisconnect() {
        initListViewData(spinner.getSelectedItemPosition());
        this.layout_inventory_hint.setVisibility(View.GONE);
        if (this.adapter.getCount() == 0) {
            this.lv_my_books.setVisibility(View.GONE);
            this.tv_no_conn_hint.setVisibility(View.VISIBLE);
        } else {
            this.lv_my_books.setVisibility(View.VISIBLE);
            this.tv_no_conn_hint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onInternetConnect() {
        initListViewData(spinner.getSelectedItemPosition());
        this.tv_no_conn_hint.setVisibility(View.GONE);
        if (this.adapter.getCount() == 0) {
            this.lv_my_books.setVisibility(View.GONE);
            this.layout_inventory_hint.setVisibility(View.VISIBLE);
        } else {
            this.lv_my_books.setVisibility(View.VISIBLE);
            this.layout_inventory_hint.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListViewData(0);
        this.updateView();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //userController.saveOfflineCommands();
        NetworkStateManager.getInstance().removeViewObserver(this);
    }
}
