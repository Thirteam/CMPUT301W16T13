package cmput301.textbookhub.Controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.OfflineHelper;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/3/16.
 *
 * Controller responsible for maintaining usr info throughout the life of the APP, uses singleton design pattern
 */
public class AppUserController extends BaseController{

    private static AppUserController instance;
    private User user;

    private OfflineHelper offlineHelper = new OfflineHelper();

    private AppUserController(){}

    public void setAppUser(User u){
        this.user = u;
        user.getBookShelf().populateBookShelf(this.queryAllTextbooks(user.getName()));
    }

    public User getAppUser(){
        return user;
    }

    public void clearAppUser(){
        user = null;
    }

    public static AppUserController getInstance(){
        if(instance == null)
            instance = new AppUserController();
        return instance;
    }

    public void saveTextBook(TextBook book){
        DataHelper.AddTextbookTask execute = new DataHelper.AddTextbookTask();
        execute.execute(book);
        getAppUser().getBookShelf().getAllBooks().add(book);
    }

    public ArrayList getAllBooksList(){
        return getAppUser().getBookShelf().getAllBooks();
    }

    public ArrayList getAvailableBooksList(){
        return getAppUser().getBookShelf().getAvailableBooks();
    }

    public ArrayList getBorrowedBooksList(){
        return getAppUser().getBookShelf().getBorrowedBooks();
    }

    public boolean registerNewUser(String username, String password, String email){
        if (isUsernameAvailable(username)) {
            User u = new User(username, password, email);
            setAppUser(u);
            DataHelper.AddUserTask t = new DataHelper.AddUserTask();
            t.execute(u);
            return true;
        }
        return false;
    }

    public boolean updateExistingUser(String password, String email){
        if(Tools.isStringValid(password) && Tools.isStringValid(email)) {
            getAppUser().setPassword(password);
            getAppUser().setEmail(email);
            DataHelper.UpdateUserTask t = new DataHelper.UpdateUserTask();
            t.execute(getAppUser());
            return true;
        }
        return false;
    }

    public boolean userAuthSuccess(Context context, String username, String password){
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
                    this.setAppUser(user.get(0));
                    saveOfflineUserprofile(context, user.get(0));
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    public boolean isUsernameAvailable(String username){
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

    public User getOfflineUserProfile(Context ctx) throws NoOfflineUserProfileFoundException{
        User u = this.offlineHelper.loadUserFromFile(ctx);
        if(u != null)
            return u;
        throw new NoOfflineUserProfileFoundException();
    }

    public void saveOfflineUserprofile(Context ctx, User u){
        this.offlineHelper.saveUserToFile(ctx, u);
    }

    public static class NoOfflineUserProfileFoundException extends Exception{}
}
