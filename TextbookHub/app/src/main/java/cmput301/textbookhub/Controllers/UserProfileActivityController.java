package cmput301.textbookhub.Controllers;

import android.content.Context;

import java.util.ArrayList;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/3/10.
 */
public class UserProfileActivityController extends BaseController {

    private static UserProfileActivityController instance;

    private UserProfileActivityController() {
    }

    public static UserProfileActivityController getInstance(String username) {
        if (instance == null) {
            instance = new UserProfileActivityController();
        }
        if(username != null) {
            instance.initAppUser(username);
        }
        return instance;
    }

    private boolean isUsernameAvailable(String username){
        DataHelper.GetUserTask t = new DataHelper.GetUserTask();
        t.execute(username);
        try{
            ArrayList<User> user = t.get();
            if(user.size() > 1){
                throw new ElasticSearchQueryException("Query username: "+username+" should only return one result but got "+user.size()+" \n");}
            return user.size() == 0;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public boolean registerNewUser(Context ctx, String username, String password, String email){
        if(Tools.isStringValid(username) && Tools.isStringValid(password) && Tools.isStringValid(email)) {
            if (isUsernameAvailable(username)) {
                User u = new User(username, password, email);
                setAppUser(u);
                DataHelper.AddUserTask t = new DataHelper.AddUserTask();
                t.execute(u);
                return true;
            } else {
                displayNotificationDialog(ctx, ctx.getResources().getString(R.string.error), ctx.getResources().getString(R.string.username_exists));
            }
        }else{
            displayNotificationDialog(ctx, ctx.getResources().getString(R.string.error), ctx.getResources().getString(R.string.user_profile_empty));
        }
        return false;
    }

    public boolean updateExistingUser(String username, String password, String email){
        if(Tools.isStringValid(username) && Tools.isStringValid(password) && Tools.isStringValid(email)) {
            User u = new User(username, password, email);
            setAppUser(u);
            DataHelper.AddUserTask t = new DataHelper.AddUserTask();
            t.execute(u);
            return true;
        }
        return false;
    }

}
