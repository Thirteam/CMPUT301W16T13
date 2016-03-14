
package cmput301.textbookhub;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_UserProfile;

/**
 * Created by runqiwang on 16-03-13.
 */
public class UserProfileTest extends ActivityInstrumentationTestCase2 {
    public UserProfileTest(){super(Activity_UserProfile.class);}

    public void testUserNameEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_UserProfile aup = (Activity_UserProfile) getActivity();

        EditText UserNameET = (EditText) aup.findViewById(R.id.et_username);
        ViewAsserts.assertOnScreen(aup.getWindow().getDecorView(), UserNameET);
    }

    public void testPassWordEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_UserProfile aup = (Activity_UserProfile) getActivity();

        EditText PassWordET = (EditText) aup.findViewById(R.id.et_password);
        ViewAsserts.assertOnScreen(aup.getWindow().getDecorView(), PassWordET);
    }

    public void testEmailEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_UserProfile aup = (Activity_UserProfile) getActivity();

        EditText EmailET = (EditText) aup.findViewById(R.id.et_email);
        ViewAsserts.assertOnScreen(aup.getWindow().getDecorView(), EmailET);
    }
}
