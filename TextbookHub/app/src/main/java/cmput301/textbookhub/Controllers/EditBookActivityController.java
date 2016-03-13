package cmput301.textbookhub.Controllers;

import android.content.Context;

import java.util.ArrayList;

import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/10.
 */
public class EditBookActivityController extends BaseController{

    private static EditBookActivityController instance;

    private EditBookActivityController(){
    }

    public static EditBookActivityController getInstance(){
        if(instance == null)
            instance = new EditBookActivityController();
        return instance;
    }

    public void saveTextBook(TextBook book){

    }

}
