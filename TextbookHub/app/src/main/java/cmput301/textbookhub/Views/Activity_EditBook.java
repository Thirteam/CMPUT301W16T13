package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBar;

import cmput301.textbookhub.Controllers.EditBookActivityController;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_EditBook extends AppCompatActivity implements BaseView{

    Button btn_save, btn_cancel;
    private EditBookActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        this.controller = (EditBookActivityController)ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_EDIT_BOOK, this);

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
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save the book intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void updateView(){

    }
}
