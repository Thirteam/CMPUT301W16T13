package cmput301.textbookhub.Controllers;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.TextBook;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class EditBookActivityController extends BaseController{

    private static EditBookActivityController instance;

    private EditBookActivityController(){
    }

    public static EditBookActivityController getInstance(String username){
        if(instance == null) {
            instance = new EditBookActivityController();

        }
        instance.initAppUser(username);
        return instance;
    }

    public void saveTextBook(TextBook book){
        DataHelper.AddTextbookTask execute = new DataHelper.AddTextbookTask();
        execute.execute(book);
        try {
            getAppUser().getBookShelf().getAllBooks().addAll(execute.get());
        } catch(ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
