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

import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.ViewBookActivityController;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_ViewBook extends AppCompatActivity implements BaseView{

    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";
    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";

    Button btn_finish;
    Button btn_delete;
    Button btn_edit;
    Button btn_submit_bid;
    TextView tv_book_name;
    TextView tv_book_edition;
    TextView tv_book_cat;
    TextView tv_book_comments;
    EditText et_bid_amount;
    Button btn_current_highest_bid;
    Button btn_owner;
    LinearLayout borrower_bid_action_layout;
    LinearLayout owner_bid_action_layout;
    LinearLayout bid_section;
    ListView bid_hist;
    Context context;

    String book_id;

    private ViewBookActivityController controller;

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
        this.controller = (ViewBookActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_VIEW_BOOK, this);
        this.controller.setCurrentBook(this.book_id);

        //TODO:extract book info with id
        //TODO:if book is borrowed, disable edit, if book is not current owner's, disable. Else enable
        //initActionView();
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
        this.bidListAdapter = new BidHistListAdapter(this.context, R.layout.adapter_bid_list, controller.getCurrentBook().getBids().getBids());
        this.bid_hist.setAdapter(this.bidListAdapter);

        btn_finish = (Button) view.findViewById(R.id.button_ok);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save the book
                finish();
            }
        });

    }

    @Override
    public void updateView(){
        this.bidListAdapter.notifyDataSetChanged();
    }

    private void initActionView(){
        this.borrower_bid_action_layout = (LinearLayout) findViewById(R.id.borrower_bid_action_layout);
        this.owner_bid_action_layout = (LinearLayout) findViewById(R.id.owner_bid_action_layout);
        this.borrower_bid_action_layout.setVisibility(View.VISIBLE);
        this.owner_bid_action_layout.setVisibility(View.VISIBLE);
        if(this.controller.getCurrentBook().getBookStatus().equals(BookStatus.BORROWED)) {
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.owner_bid_action_layout.setVisibility(View.GONE);
        }
        //book belong to the user
        if(this.controller.getCurrentBook().getOwner().getID().equals(this.controller.getAppUser().getID())){
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.btn_edit = (Button) findViewById(R.id.button_edit_item);
            this.btn_delete = (Button) findViewById(R.id.button_delete_item);
            this.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.startEditBookActivity(context);
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.requestDeleteTextBook(context);
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
                    controller.requestBidUpdate(context, et_bid_amount.getText().toString());
                }
            });
        }
    }

}
