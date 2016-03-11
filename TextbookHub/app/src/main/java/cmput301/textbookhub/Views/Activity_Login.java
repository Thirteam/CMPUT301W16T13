package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.LoginActivityController;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Activity_Login extends AppCompatActivity implements BaseView{

    Button btn_login, btn_register;
    EditText et_username, et_password;
    Context context;
    private LoginActivityController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.controller = (LoginActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_LOGIN, this);

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
                b.putString(Activity_UserProfile.BUNDLE_KEY_PROFILE_TYPE, Activity_UserProfile.BUNDLE_CONTENT_ACTIVITY_TYPE_REGISTER);
                intent.putExtra(Activity_UserProfile.INTENT_EXTRAS_KEY_BUNDLE, b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void updateView(){

    }
}
