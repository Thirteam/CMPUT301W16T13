package cmput301.textbookhub.Controllers;

import android.content.Context;

import java.util.ArrayList;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.User;

/**
 * Created by Fred on 2016/3/10.
 */
public class LoginActivityController extends BaseController {

    private static LoginActivityController instance;

    private LoginActivityController() {
    }

    public static LoginActivityController getInstance(){
        if(instance == null) {
            instance = new LoginActivityController();
        }
        return instance;
    }

    public boolean userAuthSuccess(Context ctx, String username, String password){
        DataHelper.GetUserTask t = new DataHelper.GetUserTask();
        t.execute(username);
        try{
            ArrayList<User> user = t.get();
            if(user.size() > 1){
                throw new ElasticSearchQueryException("Query username: "+username+" should only return one result but got "+user.size()+" \n");}
            else if(user.size() == 0) {
                return false;
            }
            else{
                if(user.get(0).getPassword().equals(password)) {
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

}
