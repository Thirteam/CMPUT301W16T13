package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Activity_Login extends AppCompatActivity{

    Button btn_login, btn_register;
    EditText et_username, et_password;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.button_login);
        btn_register = (Button) findViewById(R.id.button_register);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        context = this;

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Main.class);
                context.startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_UserProfile.class);
                Bundle b = new Bundle();
                b.putString(ActivityHelper.BUNDLE_KEY_ACTIVITY_TYPE, Activity_UserProfile.BUNDLE_CONTENT_ACTIVITY_TYPE_REGISTER);
                intent.putExtra(ActivityHelper.INTENT_KEY_BUNDLE, b);
                context.startActivity(intent);
            }
        });
    }
}
