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

import cmput301.textbookhub.Controllers.ActivityController;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MyBidsActivityController;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/4/4.
 */
public class Activity_AroundMe extends AppCompatActivity implements BaseView{

    private Context context;
    private ListView lv_around_me;
    private BooksAroundMeListAdapter adapter;

    private AppUserController userController;
    private ActivityController activityController;
    private LinearLayout layout_no_book_around_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);

        this.activityController = ActivityControllerFactory.getControllerForView(ActivityControllerFactory.FactoryCatalog.ACTIVITY_AROUND_ME, this);
        this.userController = AppUserController.getInstance();

        this.context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_title_around_me));
        this.layout_no_book_around_hint = (LinearLayout) findViewById(R.id.layout_no_book_around);
        this.lv_around_me = (ListView) findViewById(R.id.lv_around_me);
        this.adapter = new BooksAroundMeListAdapter(context, R.layout.adapter_books_around_me, userController.getBooksAroundMe(), userController.getCurrUserLocation());
        this.lv_around_me.setAdapter(adapter);
        updateView();
        this.lv_around_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!userController.hasInternetAccess(context)) {
                    return;
                }
                if(!activityController.isOkToQuery(((BooksAroundMeListAdapter) lv_around_me.getAdapter()).getItem(position).getID())){
                    activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.wait_update));
                    return;
                }
                Intent i = new Intent(context, Activity_ViewBook.class);
                Bundle b = new Bundle();
                b.putString(Activity_ViewBook.BUNDLE_KEY_BOOK_ID, ((BooksAroundMeListAdapter) lv_around_me.getAdapter()).getItem(position).getID());
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
    public void updateView() {
        if(this.adapter.getCount() != 0){
            this.layout_no_book_around_hint.setVisibility(View.GONE);
            this.lv_around_me.setVisibility(View.VISIBLE);
        }else{
            this.layout_no_book_around_hint.setVisibility(View.VISIBLE);
            this.lv_around_me.setVisibility(View.GONE);
        }
    }
}
