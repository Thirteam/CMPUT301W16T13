package cmput301.textbookhub;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_EditBook;
import cmput301.textbookhub.Views.Activity_Login;

/**
 * Created by runqiwang on 16-03-13.
 */
public class LoginTest extends ActivityInstrumentationTestCase2 {
    public LoginTest() {
        super(Activity_EditBook.class);
    }

    public void testLoginBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        Button LoginBtn = (Button) al.findViewById(R.id.button_login);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), LoginBtn);
    }

    public void testRegisterBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        Button RegisterBtn = (Button) al.findViewById(R.id.button_register);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), RegisterBtn);
    }

    public void testUserNameEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        EditText UserNameEV = (EditText) al.findViewById(R.id.et_username);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), UserNameEV);
    }

    public void testPassWordextEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        EditText PassWordEV = (EditText) al.findViewById(R.id.et_password);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), PassWordEV);
    }

    public void testLoginTextView(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_Login al = (Activity_Login) getActivity();

        TextView LoginTV = (TextView) al.findViewById(R.id.tv_login_appname);
        ViewAsserts.assertOnScreen(al.getWindow().getDecorView(), LoginTV);
    }
}
