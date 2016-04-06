package cmput301.textbookhub.Views;


import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.ViewBookActivityController;
import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_ViewBook extends AppCompatActivity implements BaseView{

    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";
    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";
    public static final String ACTIVITY_RESULT_KEY_BOOK_ID = "BOOK_ID";
    public static final String ACTIVITY_RESULT_KEY_BOOK_RETURNED = "BOOK_RETURNED";

    private Button btn_finish;
    private Button btn_delete;
    private Button btn_edit;
    private Button btn_submit_bid;
    private Button btn_return_book;
    private Button btn_trade_location;
    private TextView tv_book_name;
    private TextView tv_book_edition;
    private TextView tv_book_cat;
    private TextView tv_book_comments;
    private TextView tv_book_status;
    private EditText et_bid_amount;
    private Button btn_current_highest_bid;
    private Button btn_owner;
    private LinearLayout borrower_bid_action_layout;
    private LinearLayout owner_bid_action_layout;
    private LinearLayout bid_section;
    private LinearLayout borrowed_section_layout;
    private EmbeddedListView lv_bid_hist;
    private ViewPager picturePager;
    private PicturePagerAdapter pagerAdapter;
    private Context context;

    private String book_id;

    private ViewBookActivityController activityController;
    private AppUserController userController;

    private ArrayAdapter<Bid> bidListAdapter;

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
        //which book are we viewing?
        if(activityController.hasInternetAccess(this)) {
            this.activityController.setCurrentBook(this.book_id);
        }else{
            this.activityController.setCurrentBook(this.userController.getOfflineBookByID(this.book_id));
        }

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
        this.tv_book_edition = (TextView) findViewById(R.id.tv_book_edition);
        this.btn_current_highest_bid = (Button) findViewById(R.id.btn_current_highest_bid);
        this.btn_owner = (Button) findViewById(R.id.button_owner);
        this.lv_bid_hist = (EmbeddedListView) findViewById(R.id.lv_bid_hist);
        this.tv_book_status = (TextView) findViewById(R.id.tv_book_status);
        this.bidListAdapter = new BidHistListAdapter(this.context, R.layout.adapter_bid_list, activityController.getCurrentBook().getBidList().getBids());
        this.lv_bid_hist.setAdapter(this.bidListAdapter);
        btn_finish = (Button) view.findViewById(R.id.button_ok);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                Log.i("SETRESULT", "broadcast id");
                i.putExtra(Activity_ViewBook.ACTIVITY_RESULT_KEY_BOOK_ID, activityController.getCurrentBook().getID());
                setResult(RESULT_OK, i);
                finish();
            }
        });
        this.picturePager = (ViewPager) findViewById(R.id.vp_pictures);
        this.pagerAdapter = new PicturePagerAdapter(context, activityController.getCurrentBook().getPictures(), picturePager, false);
        this.picturePager.setAdapter(this.pagerAdapter);
        initActionView();
        updateView();
        //Toast.makeText(this, "Size:"+picturePager.getAdapter().getCount(),Toast.LENGTH_LONG).show();
    }



    @Override
    public void updateView(){
        bidListAdapter.notifyDataSetChanged();
        Log.i("JID is---->", "JID:"+activityController.getCurrentBook().getJid());
        this.tv_book_name.setText(this.activityController.getCurrentBook().getName());
        this.tv_book_cat.setText(this.activityController.getCurrentBook().getCategory());
        this.tv_book_comments.setText(this.activityController.getCurrentBook().getComments());
        this.tv_book_edition.setText(this.activityController.getCurrentBook().getEdition());
        this.btn_owner.setText(this.activityController.getCurrentBook().getOwner());
        this.tv_book_status.setText(this.activityController.getCurrentBook().getBookStatus().toString());
        if(!this.activityController.getCurrentBook().getBookStatus().equals(BookStatus.BORROWED)) {
            this.btn_current_highest_bid.setText(this.activityController.getCurrentBook().getBidList().getHighestBid().toString());
        }
        if(this.pagerAdapter.getCount() == 0){
            this.picturePager.setVisibility(View.GONE);
        }else{
            this.picturePager.setVisibility(View.VISIBLE);
        }
    }

    private void initActionView(){
        this.borrower_bid_action_layout = (LinearLayout) findViewById(R.id.borrower_bid_action_layout);
        this.owner_bid_action_layout = (LinearLayout) findViewById(R.id.owner_bid_action_layout);
        this.borrowed_section_layout = (LinearLayout) findViewById(R.id.borrower_return_layout);
        this.borrower_bid_action_layout.setVisibility(View.VISIBLE);
        this.owner_bid_action_layout.setVisibility(View.VISIBLE);
        this.borrowed_section_layout.setVisibility(View.VISIBLE);
        initOwnerButton();
        //book may or may not belong to the current user
        //book is currently borrowed
        String username = this.activityController.getCurrentBook().getOwner();
        if(this.activityController.getCurrentBook().getBookStatus().equals(BookStatus.BORROWED)) {
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.owner_bid_action_layout.setVisibility(View.GONE);
            this.bid_section.setVisibility(View.GONE);
            if(IamTheOwnerOfThisBook(username)) {
                this.borrowed_section_layout.setVisibility(View.GONE);
            }else if(this.activityController.getCurrentBook().getBorrower().equals(userController.getAppUser().getName())){
                this.borrowed_section_layout.setVisibility(View.VISIBLE);
                this.btn_return_book = (Button) findViewById(R.id.button_return);
                this.btn_trade_location = (Button) findViewById(R.id.button_location);
                this.btn_return_book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!activityController.hasInternetAccess(context)) {
                            activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.offline_no_server));
                            finish();
                        }
                        activityController.setBookReturned();
                        activityController.updateCurrentTextbook();
                        Intent i = getIntent();
                        i.putExtra(Activity_ViewBook.ACTIVITY_RESULT_KEY_BOOK_ID, activityController.getCurrentBook().getID());
                        i.putExtra(ACTIVITY_RESULT_KEY_BOOK_RETURNED, ACTIVITY_RESULT_KEY_BOOK_RETURNED);
                        setResult(RESULT_OK, i);
                        finish();//On activity result is needed
                    }
                });
                this.btn_trade_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!activityController.hasInternetAccess(context)) {
                            activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.offline_no_server));
                            finish();
                        }
                        //launch map activity
                        Intent i = new Intent(context, Activity_Map.class);
                        i.putExtra(Activity_Map.ACTIVITY_KEY_LAT, activityController.getCurrentBook().getLat());
                        i.putExtra(Activity_Map.ACTIVITY_KEY_LONG, activityController.getCurrentBook().getLon());
                        i.putExtra(Activity_Map.ACTIVITY_TYPE, Activity_Map.ACTIVITY_VIEW_ONLY);
                        startActivity(i);
                    }
                });
            }else{
                this.borrowed_section_layout.setVisibility(View.GONE);
            }
            return;
        }
        //book belongs to the user
        //user can edit or delete this book
        initHighestBidButton();
        this.borrowed_section_layout.setVisibility(View.GONE);
        this.activityController.updateViewStatus();
        if(!this.activityController.hasInternetAccess(context) || IamTheOwnerOfThisBook(username)){
            this.bid_section.setVisibility(View.VISIBLE);
            this.borrower_bid_action_layout.setVisibility(View.GONE);
            this.btn_edit = (Button) findViewById(R.id.button_edit_item);
            this.btn_delete = (Button) findViewById(R.id.button_delete_item);
            this.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activityController.getCurrentBook().hasValidBids()) {
                        AlertDialog.Builder b = new AlertDialog.Builder(context);
                        b.setTitle(getResources().getString(R.string.warning));
                        b.setMessage(getResources().getString(R.string.edit_warning));
                        b.setPositiveButton(getResources().getString(R.string.yes_en), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                activityController.startEditBookActivity(context);
                                finish();
                            }
                        });
                        b.setNegativeButton(getResources().getString(R.string.no_en), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        b.show();
                    }else {
                        activityController.startEditBookActivity(context);
                        finish();
                    }
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle(getResources().getString(R.string.warning));
                    b.setMessage(getResources().getString(R.string.delete_confirm));
                    b.setPositiveButton(getResources().getString(R.string.yes_en), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            userController.requestDeleteTextBook(activityController.getCurrentBook());
                            finish();
                        }
                    });
                    b.setNegativeButton(getResources().getString(R.string.no_en), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    b.show();
                }
            });
        }else{
            //book does not belong to the current user
            //book is available and can be bid on
            this.bid_section.setVisibility(View.VISIBLE);
            this.owner_bid_action_layout.setVisibility(View.GONE);
            this.btn_submit_bid = (Button) findViewById(R.id.button_place_bid);
            this.et_bid_amount = (EditText) findViewById(R.id.et_bid_amount);
            this.btn_submit_bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!activityController.hasInternetAccess(context)) {
                        activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.offline_no_server));
                        finish();
                        return;
                    }
                    if (activityController.isBidValid(et_bid_amount.getText().toString())) {
                        Double d = Double.parseDouble(et_bid_amount.getText().toString());
                        Bid b = new Bid( Double.valueOf(Tools.roundDecimal(2, d)), userController.getAppUser());
                        activityController.addNewValidBid(b);
                        activityController.updateCurrentTextbook();
                        et_bid_amount.getText().clear();
                        activityController.clearOnScreenKsyboard((Activity_ViewBook) context);
                        updateView();
                        Toast.makeText(context, context.getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                    } else {
                        activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.invalid_bid_entered));
                    }

                }
            });
        }
    }

    private void initOwnerButton(){
        this.btn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = activityController.getOwnerInfo();
                makeOwnerInfoDialog(u).show();
            }
        });
    }

    private void initHighestBidButton(){
        this.btn_current_highest_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBidInfoDialog(activityController.getCurrentBook().getBookHighestBidAmount().toString(), activityController.getCurrentBook().getBidList().getHighestBid().getBidder(), activityController.getCurrentBook().getBidList().getHighestBid().getTimestamp()).show();
            }
        });
    }

    private boolean IamTheOwnerOfThisBook(String username){
        return this.activityController.queryUser(username).getID().equals(this.userController.getAppUser().getID());
    }

    private AlertDialog makeOwnerInfoDialog(User u){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_owner_info, null);
        dialogBuilder.setView(dialogView);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(u.getTimestamp());
        String date = formatter.format(c.getTime());
        ((TextView) dialogView.findViewById(R.id.tv_username)).setText(u.getName());
        ((TextView) dialogView.findViewById(R.id.tv_email)).setText(u.getEmail());
        ((TextView) dialogView.findViewById(R.id.tv_member_since)).setText(date);
        dialogBuilder.setTitle(getResources().getString(R.string.title_owner_info));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        return dialogBuilder.create();
    }

    private AlertDialog makeBidInfoDialog(String amount, String username, String time){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_bid_info, null);
        dialogBuilder.setView(dialogView);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(new Long(time));
        String date = formatter.format(c.getTime());
        ((TextView) dialogView.findViewById(R.id.tv_username)).setText(username);
        ((TextView) dialogView.findViewById(R.id.tv_bid_amount)).setText(amount);
        ((TextView) dialogView.findViewById(R.id.tv_date)).setText(date);
        dialogBuilder.setTitle(getResources().getString(R.string.title_bid_info));
        dialogBuilder.setNegativeButton(getResources().getString(R.string.finish_en), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        if(IamTheOwnerOfThisBook(this.activityController.getCurrentBook().getOwner())) {
            dialogBuilder.setPositiveButton(getResources().getString(R.string.accept_bid), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    if (activityController.isBidderValid()) {
                        //accept this bid
                        activityController.setBookBorrowed();
                        userController.updateExistingPersonalTextbook(activityController.getCurrentBook());
                        finish();
                    } else {
                        activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.error_own_bid));
                    }
                }
            });
        }
        return dialogBuilder.create();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseApplication.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.activityResumed();
    }

}
