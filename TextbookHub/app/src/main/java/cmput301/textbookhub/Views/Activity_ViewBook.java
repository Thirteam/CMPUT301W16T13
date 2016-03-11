package cmput301.textbookhub.Views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBar;

import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.ViewBookActivityController;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_ViewBook extends AppCompatActivity implements BaseView{

    public static final String BUNDLE_KEY_BOOK_ID = "BOOK_ID";
    public static final String INTENT_EXTRAS_KEY_BUNDLE = "BUNDLE";

    Button btn_finish;
    String book_id;

    private ViewBookActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_info);

        this.controller = (ViewBookActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_VIEW_BOOK, this);

        Bundle b = getIntent().getExtras().getBundle(INTENT_EXTRAS_KEY_BUNDLE);
        if(b != null){
            this.book_id = b.getString(BUNDLE_KEY_BOOK_ID);
        }
        //TODO:extract book info with id
        //TODO:if book is borrowed, disable edit, if book is not current owner's, disable. Else enable

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_ok,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
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

    }
}
