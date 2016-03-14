package cmput301.textbookhub;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet4Address;

import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.Activity_EditBook;
import cmput301.textbookhub.Views.Activity_Login;

/**
 * Created by runqiwang on 16-03-13.
 */
public class EditBookTest extends ActivityInstrumentationTestCase2 {
    public EditBookTest() {super(Activity_EditBook.class);}

    public void testAddPictureBtn(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_EditBook ae = (Activity_EditBook) getActivity();

        Button AddPictureBtn = (Button) ae.findViewById(R.id.button_take_pic);
        ViewAsserts.assertOnScreen(ae.getWindow().getDecorView(), AddPictureBtn);
    }

    public void testBookNameEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_EditBook ae = (Activity_EditBook) getActivity();

        EditText BookNameET = (EditText) ae.findViewById(R.id.et_book_name);
        ViewAsserts.assertOnScreen(ae.getWindow().getDecorView(), BookNameET);
    }

    public void testBookBidEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_EditBook ae = (Activity_EditBook) getActivity();

        EditText BookBidET = (EditText) ae.findViewById(R.id.et_starting_bid);
        ViewAsserts.assertOnScreen(ae.getWindow().getDecorView(), BookBidET);
    }

    public void testEditionEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_EditBook ae = (Activity_EditBook) getActivity();

        EditText EditionET = (EditText) ae.findViewById(R.id.et_edition);
        ViewAsserts.assertOnScreen(ae.getWindow().getDecorView(), EditionET);
    }

    public void testCommentEditText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        Activity_EditBook ae = (Activity_EditBook) getActivity();

        EditText CommentET = (EditText) ae.findViewById(R.id.et_comments);
        ViewAsserts.assertOnScreen(ae.getWindow().getDecorView(), CommentET);
    }
}
