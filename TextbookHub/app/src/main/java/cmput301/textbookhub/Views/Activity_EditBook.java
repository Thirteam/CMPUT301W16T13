package cmput301.textbookhub.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.widget.EditText;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.EditBookActivityController;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class Activity_EditBook extends AppCompatActivity implements BaseView{

    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";
    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";

    private Button btn_save, btn_cancel;
    private EditBookActivityController controller;
    private Button btn_add_pic;
    private EditText et_book_name, et_book_edition, et_book_comments,  et_starting_bid;
    private AutoCompleteTextView et_book_category;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        this.context = this;

        this.controller = (EditBookActivityController)ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_EDIT_BOOK, this, ((BaseApplication)getApplication()).getAppUsername());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_edit,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        btn_save = (Button) view.findViewById(R.id.button_save);
        btn_cancel = (Button) view.findViewById(R.id.button_cancel);
        this.btn_add_pic = (Button) findViewById(R.id.button_take_pic);
        this.et_book_name = (EditText) findViewById(R.id.et_book_name);
        this.et_starting_bid = (EditText) findViewById(R.id.et_starting_bid);
        this.et_book_category = (AutoCompleteTextView) findViewById(R.id.et_category);
        this.et_book_edition = (EditText) findViewById(R.id.et_edition);
        this.et_book_comments = (EditText) findViewById(R.id.et_comments);
        this.et_starting_bid = (EditText) findViewById(R.id.et_starting_bid);
        this.et_book_category.setAdapter( new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.category_array)));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save the book
                if(Tools.isStringValid(et_book_name.getText().toString())) {
                    TextBook book = new TextBook.Builder(controller.getAppUser(), et_book_name.getText().toString())
                            .addCategory(et_book_category.getText().toString()).addComments(et_book_comments.getText().toString())
                            .addStartingBid(et_starting_bid.getText().toString(), controller.getAppUser()).addEdition(et_book_edition.getText().toString()).buildTextBook();
                    controller.saveTextBook(book);
                    finish();
                }else{
                    controller.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.invalid_book_name));
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().hasExtra(INTENT_EXTRAS_KEY_BUNDLE)){
            Bundle b = getIntent().getExtras().getBundle(INTENT_EXTRAS_KEY_BUNDLE);
            initEditBookValues(b.getString(BUNDLE_KEY_BOOK_ID));
        }
    }

    @Override
    public void updateView(){

    }

    public void initEditBookValues(String id){
        TextBook book = this.controller.queryTextbook(id);
        this.et_book_name.setText(book.getName());
        book.getBidList().sortBidsByAmount();
        this.et_starting_bid.setText(book.getBidList().getBids().get(
                book.getBidList().getBids().size() - 1
        ).toString());
        this.et_book_comments.setText(book.getComments());
        this.et_book_edition.setText(book.getEdition());
        this.et_book_category.setText(book.getCategory());
    }
}
