package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_EditBook extends AppCompatActivity {

    Button btn_save, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_edit,
                null);
        android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        btn_save = (Button) view.findViewById(R.id.button_save);
        btn_cancel = (Button) view.findViewById(R.id.button_cancel);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save the book
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
}
