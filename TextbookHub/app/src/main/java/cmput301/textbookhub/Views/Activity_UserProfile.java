package cmput301.textbookhub.Views;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.UserProfileActivityController;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/2.
 */
public class Activity_UserProfile extends AppCompatActivity implements BaseView{

    public static final String BUNDLE_KEY_PROFILE_TYPE = "Activity_Type";
    public static final String INTENT_EXTRAS_KEY_BUNDLE = "Bundle";
    public static final String BUNDLE_CONTENT_ACTIVITY_TYPE_REGISTER = "Register";
    public static final String BUNDLE_CONTENT_ACTIVITY_TYPE_VIEW = "View";
    private static final String STATUS_EDIT_ENABLED = "Edit";
    private static final String STATUS_VIEW_ONLY = "View";
    private String status = STATUS_VIEW_ONLY;
    private Context context;

    private Button btn_save, btn_finish;
    private EditText et_username, et_password, et_email;

    private UserProfileActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.controller = (UserProfileActivityController)ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_USER_PROFILE, this, ((BaseApplication) getApplication()).getAppUsername());

        context = this;
        // Set your custom view
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //View view = getSupportActionBar().getCustomView();
        View view = getLayoutInflater().inflate(R.layout.actionbar_buttonbar_edit,
                null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setCustomView(view, layoutParams);
        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);


        final Bundle extras = getIntent().getBundleExtra(INTENT_EXTRAS_KEY_BUNDLE);
        if (extras != null) {
            initializeView(extras, view);
        }
    }

    private void initializeView(Bundle extras, View v){
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        et_email = (EditText)findViewById(R.id.et_email);
        btn_save = (Button) v.findViewById(R.id.button_save);
        btn_finish = (Button)v.findViewById(R.id.button_cancel);
        btn_finish.setText(getResources().getString(R.string.finish_en));
        if(extras.getString(BUNDLE_KEY_PROFILE_TYPE, BUNDLE_CONTENT_ACTIVITY_TYPE_VIEW).equals(BUNDLE_CONTENT_ACTIVITY_TYPE_VIEW)){
            btn_save.setText(getResources().getString(R.string.edit_en));
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == STATUS_VIEW_ONLY) {
                        status = STATUS_EDIT_ENABLED;
                        enableEditTexts();
                        btn_save.setText(getResources().getString(R.string.save_en));
                        btn_finish.setText(getResources().getString(R.string.cancel_en));
                    } else {
                        //TODO:Update user profile
                        //TODO:Fix app crashes when user login when the changed password
                        //initEditTextValues();

                        if(controller.updateExistingUser(et_username.getText().toString(), et_password.getText().toString(), et_email.getText().toString())) {
                            ((BaseApplication)getApplication()).setAppUsername(et_username.getText().toString());
                            Intent intent = new Intent(context, Activity_Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                        //if(controller.registerNewUser(context, et_username.getText().toString(), et_password.getText().toString(), et_email.getText().toString())) {
                            //finish();
                       // }
                    }
                }
            });
            btn_finish.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (status == STATUS_VIEW_ONLY) {
                        finish();
                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle(getResources().getString(R.string.discard_changes));
                        dialog.setMessage(getResources().getString(R.string.unsaved_warning));
                        dialog.setPositiveButton(getResources().getString(R.string.yes_en), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        dialog.setNegativeButton(getResources().getString(R.string.no_en), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            });
            initEditTextValues();
            disableEditTexts();
        }else{
            btn_save.setText(getResources().getString(R.string.register));
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: check input data integrity, create new user, go to main page
                    if(controller.registerNewUser(context, et_username.getText().toString(), et_password.getText().toString(), et_email.getText().toString())) {
                        ((BaseApplication)getApplication()).setAppUsername(et_username.getText().toString());
                        Intent intent = new Intent(context, Activity_Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                }
            });
            btn_finish.setText(getResources().getString(R.string.cancel_en));
            btn_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    private void initEditTextValues() {
        et_username.setText(this.controller.getAppUser().getName());
        et_password.setText(this.controller.getAppUser().getPassword());
        et_email.setText(this.controller.getAppUser().getEmail());
    }

    private void enableEditTexts(){
        //et_username.setEnabled(true);
        et_password.setEnabled(true);
        et_email.setEnabled(true);
    }
    private void disableEditTexts(){
        et_username.setEnabled(false);
        et_password.setEnabled(false);
        et_email.setEnabled(false);
    }

    @Override
    public void updateView(){

    }
}
