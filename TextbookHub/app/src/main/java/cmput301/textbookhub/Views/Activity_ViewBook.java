package cmput301.textbookhub.Views;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.ViewBookActivityController;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_ViewBook extends AppCompatActivity implements BaseView{

    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";
    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";

    private Button btn_finish;
    private Button btn_delete;
    private  Button btn_edit;
    private  Button btn_submit_bid;
    private  TextView tv_book_name;
    private  TextView tv_book_edition;
    private  TextView tv_book_cat;
    private  TextView tv_book_comments;
    private  TextView tv_book_status;
    private  EditText et_bid_amount;
    private  Button btn_current_highest_bid;
    private  Button btn_owner;
    private  LinearLayout borrower_bid_action_layout;
    private  LinearLayout owner_bid_action_layout;
    private  LinearLayout bid_section;
    private  ListView bid_hist;
    private Context context;

    private String book_id;

    private ViewBookActivityController activityController;
    private AppUserController userController;

    private ArrayAdapter bidListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_info);
        this.context = this;
        Bundle b = getIntent().getExtras().getBundle(INTENT_EXTRAS_KEY_BUNDLE);
        if(b != null){
            this.book_id = b.getString(BUNDLE_KEY_BOOK_ID);
        }

        this.activityController = (ViewBookActivityController) ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_VIEW_BOOK, this);
        this.userController = AppUserController.getInstance();

        this.activityController.setCurrentBook(this.book_id);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_ok,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        this.bid_section = (LinearLayout) findViewById(R.id.bid_section);
        this.tv_book_name = (TextView) findViewById(R.id.tv_book_name);
        this.tv_book_cat = (TextView) findViewById(R.id.tv_book_cat);
        this.tv_book_comments = (TextView) findViewById(R.id.tv_book_comments);
        this.tv_book_edition = (TextView) findViewById(R.id.tv_book_cat);
        this.btn_current_highest_bid = (Button) findViewById(R.id.btn_current_highest_bid);
        this.btn_owner = (Button) findViewById(R.id.button_owner);
        this.bid_hist = (ListView) findViewById(R.id.lv_bid_hist);
        this.tv_book_status = (TextView) findViewById(R.id.tv_book_status);
        this.bidListAdapter = new BidHistListAdapter(this.context, R.layout.adapter_bid_list, activityController.getCurrentBook().getBidList().getBids());
        this.bid_hist.setAdapter(this.bidListAdapter);

        btn_finish = (Button) view.findViewById(R.id.button_ok);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initActionView();
        updateView();
    }

    @Override
    public void updateView(){
        this.bidListAdapter.notifyDataSetChanged();
        this.tv_book_name.setText(this.activityController.getCurrentBook().getName());
        this.tv_book_cat.setText(this.activityController.getCurrentBook().getCategory());
        this.tv_book_comments.setText(this.activityController.getCurrentBook().getComments());
        this.tv_book_edition.setText(this.activityController.getCurrentBook().getEdition());
        this.btn_owner.setText(this.userController.getAppUser().getName());
        this.tv_book_status.setText(this.activityController.getCurrentBook().getBookStatus().toString());
        if(this.activityController.getCurrentBook().getBookStatus().equals(BookStatus.AVAILABLE))
            this.btn_current_highest_bid.setText(this.activityController.getCurrentBook().getBidList().getHighestBid().toString());
    }

    private void initActionView(){
        this.borrower_bid_action_layout = (LinearLayout) findViewById(R.id.borrower_bid_action_layout);
        this.owner_bid_action_layout = (LinearLayout) findViewById(R.id.owner_bid_action_layout);
        this.borrower_bid_action_layout.setVisibility(View.VISIBLE);
        this.owner_bid_action_layout.setVisibility(View.VISIBLE);
        if(this.activityController.getCurrentBook().getBookStatus().equals(BookStatus.BORROWED)) {
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.owner_bid_action_layout.setVisibility(View.GONE);
        }
        //book belong to the user
        if(this.activityController.getCurrentBook().getOwner().getID().equals(this.userController.getAppUser().getID())){
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.btn_edit = (Button) findViewById(R.id.button_edit_item);
            this.btn_delete = (Button) findViewById(R.id.button_delete_item);
            this.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityController.startEditBookActivity(context);
                    finish();
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityController.requestDeleteTextBook(context);
                    finish();
                }
            });
        }else{
            this.owner_bid_action_layout.setVisibility(View.GONE);
            this.btn_submit_bid = (Button) findViewById(R.id.button_place_bid);
            this.et_bid_amount = (EditText) findViewById(R.id.et_bid_amount);
            this.btn_submit_bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityController.requestBidUpdate(context, et_bid_amount.getText().toString(), userController.getAppUser());
                }
            });
        }
    }

}
