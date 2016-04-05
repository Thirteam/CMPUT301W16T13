package cmput301.textbookhub;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_Main;
import cmput301.textbookhub.Views.Activity_UserProfile;
import cmput301.textbookhub.Views.Fragment_Search;

/**
 * Created by runqiwang on 16-03-13.
 */
public class LoginTest extends ActivityInstrumentationTestCase2 {
    private Button button_login;
    private Button button_register;
    private EditText username;
    private EditText password;

    public LoginTest() {
        super(Activity_Login.class);
    }

    //Make sure Login Button is showed
    public void testLoginBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        Button LoginBtn = (Button) al.findViewById(R.id.button_login);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), LoginBtn);
    }
    //Make sure Register Button is showed
    public void testRegisterBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        Button RegisterBtn = (Button) al.findViewById(R.id.button_register);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), RegisterBtn);
    }
    //Make sue UserName Edit Text is showed
    public void testUserNameEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        EditText UserNameEV = (EditText) al.findViewById(R.id.et_username);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), UserNameEV);
    }
    //Make sure PassWord Edit Text is showed
    public void testPassWordextEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        EditText PassWordEV = (EditText) al.findViewById(R.id.et_password);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), PassWordEV);
    }
    //Make sure the Login Title Text View is showed
    public void testLoginTextView(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        TextView LoginTV = (TextView) al.findViewById(R.id.tv_login_appname);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), LoginTV);
    }


    public void testLoginActivity() {
        // starts login
        Activity_Login al = (Activity_Login) getActivity();
        // user clicks on tweet they want to edit
        username = al.getEt_username();
        password = al.getEt_password();
        al.runOnUiThread(new Runnable() {
            public void run() {
                username.setText("xylove");
                password.setText("1234");
            }
        });
        getInstrumentation().waitForIdleSync(); // make sure our UI thread finished

        Instrumentation.ActivityMonitor receiverActivityMonitor1 = getInstrumentation().addMonitor(Activity_Main.class.getName(),null, false);

        button_login = al.getBtn_login();
        al.runOnUiThread(new Runnable() {
            public void run() {
                button_login.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // make sure our UI thread finished

        // Following was stolen from https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-10-13
        // Set up an ActivityMonitor
        // Validate that ReceiverActivity is started
        Activity_Main receiverActivity = (Activity_Main) receiverActivityMonitor1.waitForActivityWithTimeout(1000);

        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, receiverActivityMonitor1.getHits());
        assertEquals("Activity is of wrong type", Activity_Main.class, receiverActivity.getClass());
        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor1);
    }

    public void testRegisterActivity() {

        // starts login
        Activity_Login al = (Activity_Login) getActivity();
        // user clicks on tweet they want to edit
        username = al.getEt_username();
        password = al.getEt_password();
        al.runOnUiThread(new Runnable() {
            public void run() {
                username.setText("xylove");
                password.setText("1234");
            }
        });

        Instrumentation.ActivityMonitor receiverActivityMonitor2 = getInstrumentation().addMonitor(Activity_UserProfile.class.getName(),null, false);

        button_register = al.getBtn_register();
        al.runOnUiThread(new Runnable() {
            public void run() {
                button_register.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // make sure our UI thread finished

        // Following was stolen from https://developer.android.com/training/activity-testing/activity-functional-testing.html 2015-10-13
        // Set up an ActivityMonitor
        // Validate that ReceiverActivity is started
        Activity_UserProfile receiverActivity = (Activity_UserProfile) receiverActivityMonitor2.waitForActivityWithTimeout(10000);

        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, receiverActivityMonitor2.getHits());
        assertEquals("Activity is of wrong type", Activity_UserProfile.class, receiverActivity.getClass());
        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor2);
    }
}
