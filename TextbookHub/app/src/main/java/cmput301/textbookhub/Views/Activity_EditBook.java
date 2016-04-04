package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.widget.EditText;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

import cmput301.textbookhub.Controllers.ActivityController;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_EditBook extends AppCompatActivity implements BaseView{

    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";
    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    static final int REQUEST_LOCATION_SETTING = 4321;

    private Button btn_save, btn_cancel;
    private ActivityController activityController;
    private AppUserController userController;
    private Button btn_add_pic, btn_set_location;
    private EditText et_book_name, et_book_edition, et_book_comments,  et_starting_bid;
    private AutoCompleteTextView et_book_category;
    private Context context;
    private ViewPager picturePager;
    private PicturePagerAdapter pagerAdapter;
    private ArrayList<Bitmap> pictures = new ArrayList<>();

    //For when we're editting textbook
    private Textbook bookEdit;

    Double lat = 53.631611;
    Double lon = -113.323975;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        this.context = this;

        //need two controllers for every activity
        this.activityController = ActivityControllerFactory.getControllerForView(ActivityControllerFactory.FactoryCatalog.ACTIVITY_EDIT_BOOK, this);
        this.userController = AppUserController.getInstance();
        //customize actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_edit, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        //get views
        btn_save = (Button) view.findViewById(R.id.button_save);
        btn_cancel = (Button) view.findViewById(R.id.button_cancel);
        this.btn_add_pic = (Button) findViewById(R.id.button_take_pic);
        this.et_book_name = (EditText) findViewById(R.id.et_book_name);
        this.et_starting_bid = (EditText) findViewById(R.id.et_starting_bid);
        this.et_book_category = (AutoCompleteTextView) findViewById(R.id.et_category);
        this.et_book_edition = (EditText) findViewById(R.id.et_edition);
        this.et_book_comments = (EditText) findViewById(R.id.et_comments);
        this.et_starting_bid = (EditText) findViewById(R.id.et_starting_bid);
        this.picturePager = (ViewPager) findViewById(R.id.vp_pictures);
        this.btn_set_location = (Button) findViewById(R.id.button_set_location);
        this.et_book_category.setAdapter( new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.category_array)));

        if(getIntent().hasExtra(INTENT_EXTRAS_KEY_BUNDLE)){
            Bundle b = getIntent().getExtras().getBundle(INTENT_EXTRAS_KEY_BUNDLE);
            bookEdit = initEditBookValues(b.getString(BUNDLE_KEY_BOOK_ID));
            this.pictures.addAll(bookEdit.getPictures());
        }else{
            LatLng loc =userController.getCurrUserLocation();
            this.lat = loc.getLatitude();
            this.lon = loc.getLongitude();
            Log.i("LAUNCHING ", "EDITBOOKACTIVITY WITHOUT VALID TRADE LOC, CURR LOC"+this.lat+" || "+this.lon);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().hasExtra(INTENT_EXTRAS_KEY_BUNDLE)){
                    //If edit window
                    if (Tools.isStringValid(et_book_name.getText().toString())) {
                        bookEdit.setBookName(et_book_name.getText().toString());
                        bookEdit.setCategory(et_book_category.getText().toString());
                        bookEdit.setEdition(et_book_edition.getText().toString());
                        bookEdit.setComments(et_book_comments.getText().toString());
                        bookEdit.setBookStatus(BookStatus.AVAILABLE);
                        bookEdit.clearAllPictures();
                        for(Bitmap p : pictures)
                            bookEdit.addPicture(p);
                        if(Tools.isDouble(et_starting_bid.getText().toString()))
                            bookEdit.setStartingBidAmount(et_starting_bid.getText().toString());
                        else
                            bookEdit.setStartingBidAmount("0.0");
                        bookEdit.setLat(lat);
                        bookEdit.setLon(lon);
                        userController.updateExistingPersonalTextbook(bookEdit);
                        finish();
                    } else {
                        activityController.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.invalid_book_name));
                    }
                }else {
                    if(Tools.isStringValid(et_book_name.getText().toString())) {
                        Textbook book = new Textbook.Builder(userController.getAppUser(), et_book_name.getText().toString())
                                .addCategory(et_book_category.getText().toString()).addComments(et_book_comments.getText().toString()).addLat(lat).addLon(lon)
                                .addPictures(pictures).addStartingBid(et_starting_bid.getText().toString(), userController.getAppUser()).addEdition(et_book_edition.getText().toString()).buildTextBook();
                        userController.saveTextBook(book);
                        finish();
                    }else {
                        activityController.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.invalid_book_name));
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.pagerAdapter = new PicturePagerAdapter(context, this.pictures, this.picturePager, true){
            @Override
            public void removeBitmapAt(ViewPager pager, int index) {
                super.removeBitmapAt(pager, index);
                updateView();
            }

            @Override
            public void addBitmap(Bitmap map) {
                super.addBitmap(map);
                updateView();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                updateView();
            }
        };
        this.picturePager.setAdapter(pagerAdapter);
        this.btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        this.btn_set_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityController.hasInternetAccess(context)){
                    Intent i = new Intent(context, Activity_Map.class);
                    i.putExtra(Activity_Map.ACTIVITY_KEY_LAT, lat);
                    i.putExtra(Activity_Map.ACTIVITY_KEY_LONG, lon);
                    i.putExtra(Activity_Map.ACTIVITY_TYPE, Activity_Map.ACTIVITY_SET_LOC);
                    startActivityForResult(i, REQUEST_LOCATION_SETTING);
                }else{
                    activityController.displayNotificationDialog(context, getResources().getString(R.string.warning), getResources().getString(R.string.default_location_used));
                }
            }
        });

        if(!activityController.hasInternetAccess(context))
            activityController.displayNotificationDialog(context, context.getResources().getString(R.string.offline_title), context.getResources().getString(R.string.offline_new_book));

        updateView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data .getExtras();
            Bitmap new_pic = (Bitmap) extras.get("data");
            this.pagerAdapter.addBitmap(new_pic);
            this.pagerAdapter.notifyDataSetChanged();
            updateView();
        }
        if(requestCode == REQUEST_LOCATION_SETTING && resultCode == RESULT_OK){
            this.lat = data.getDoubleExtra(Activity_Map.ACTIVITY_KEY_LAT, lat);
            this.lon = data.getDoubleExtra(Activity_Map.ACTIVITY_KEY_LONG, lon);
        }
    }

    @Override
    public void updateView(){
        if(this.pagerAdapter.getCount() == 0){
            this.picturePager.setVisibility(View.GONE);
        }else{
            this.picturePager.setVisibility(View.VISIBLE);
        }
    }

    public Textbook initEditBookValues(String id){
        Textbook textbook;
        if(userController.hasInternetAccess(this)) {
            textbook = this.userController.queryTextbook(id);
        }else{
            textbook = this.userController.getOfflineBookByID(id);
        }
        this.et_book_name.setText(textbook.getName());
        textbook.getBidList().sortBidsByAmount();
        this.et_starting_bid.setText(textbook.getBidList().getBids().get(
                textbook.getBidList().getBids().size() - 1
        ).toString());
        this.et_book_comments.setText(textbook.getComments());
        this.et_book_edition.setText(textbook.getEdition());
        this.et_book_category.setText(textbook.getCategory());
        this.lat = textbook.getLat();
        this.lon = textbook.getLon();
        return textbook;
    }
}
