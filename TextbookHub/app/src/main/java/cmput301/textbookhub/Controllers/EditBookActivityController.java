package cmput301.textbookhub.Controllers;

import android.os.AsyncTask;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.TextBook;

/**
 * Created by Fred on 2016/3/10.
 */
public class EditBookActivityController extends BaseController{

    private static EditBookActivityController instance;

    private EditBookActivityController(){
    }

    public static EditBookActivityController getInstance(String username){
        if(instance == null) {
            instance = new EditBookActivityController();
            instance.initAppUser(username);
        }
        return instance;
    }

    public void saveTextBook(TextBook book){
        AsyncTask<TextBook, Void, Void> execute = new DataHelper.AddTextbookTask();
        execute.execute(book);
    }

}
