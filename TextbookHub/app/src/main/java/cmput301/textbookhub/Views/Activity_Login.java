package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cmput301.textbookhub.Controllers.ActivityController;
import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Activity_Login extends AppCompatActivity implements BaseView{

    private Button btn_login, btn_register;
    private EditText et_username, et_password;
    private Context context;
    private ActivityController activityController;
    private AppUserController userController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.activityController = ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_LOGIN, this);
        this.userController = AppUserController.getInstance();

        btn_login = (Button) findViewById(R.id.button_login);
        btn_register = (Button) findViewById(R.id.button_register);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username.getText().clear();
        et_password.getText().clear();
        context = this;

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityController.hasServerAccess()) {
                    if(userController.userAuthSuccess(et_username.getText().toString(), et_password.getText().toString())) {
                        Intent intent = new Intent(context, Activity_Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        finish();
                    }else{
                        activityController.displayNotificationDialog(context, getResources().getString(R.string.error), getResources().getString(R.string.auth_failure));
                    }
                }else {
                    activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.offline_no_server));
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityController.hasServerAccess()) {
                    Intent intent = new Intent(context, Activity_UserProfile.class);
                    Bundle b = new Bundle();
                    b.putString(Activity_UserProfile.BUNDLE_KEY_PROFILE_TYPE, Activity_UserProfile.BUNDLE_CONTENT_ACTIVITY_TYPE_REGISTER);
                    intent.putExtra(Activity_UserProfile.INTENT_EXTRAS_KEY_BUNDLE, b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else {
                    activityController.displayNotificationDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.offline_no_server));
                }
            }
        });

        try{
            User user = userController.getOfflineUserProfile();
            userController.setAppUser(user);
            Intent intent = new Intent(context, Activity_Main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            finish();
        }catch(AppUserController.NoOfflineUserProfileFoundException e){}
        et_username.clearFocus();
        et_password.clearFocus();
    }

    @Override
    public void updateView(){
    }

    public EditText getEt_password() {
        return et_password;
    }

    public EditText getEt_username() {
        return et_username;
    }

    public Button getBtn_login() {
        return btn_login;
    }

    public Button getBtn_register() {
        return btn_register;
    }
}
