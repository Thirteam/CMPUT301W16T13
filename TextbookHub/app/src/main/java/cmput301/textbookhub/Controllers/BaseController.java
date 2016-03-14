package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.DataBundleLabel;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.BaseView;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-08
 */
public abstract class BaseController {

    private static User user;

    private ArrayList<BaseView> views;

    public BaseController(){
        //http://cmput301.softwareprocess.es:8080/thirteam/
        this.views = new ArrayList<>();
    }

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public BookStatus findBookStatus(String name) throws IllegalArgumentException{
        for(BookStatus status : BookStatus.values()){
            if(status.equalsName(name)){
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    public DataBundleLabel findDataBundleLabel(String name) throws IllegalArgumentException{
        for(DataBundleLabel label : DataBundleLabel.values()){
            if(label.equalsName(name)){
                return label;
            }
        }
        throw new IllegalArgumentException();
    }

    public ArrayList<BaseView> getBaseViews() {
        return views;
    }

    public void setBaseViews(ArrayList<BaseView> list) {
        this.views = list;
    }

    public void addBaseView(BaseView view) {
        this.views.add(view);
    }

    public void updateBaseViews() {
        for(BaseView v : this.views)
            v.updateView();
    }

    public User getAppUser(){
        return user;
    }

    private User queryAppUser(String username){
        DataHelper.GetUserTask t = new DataHelper.GetUserTask();
        t.execute(username);
        try{
            ArrayList<User> user = t.get();
            if(user.size() > 1){
                throw new ElasticSearchQueryException("Query username: "+username+" should only return one result but got "+user.size()+" \n");}
            else if(user.size() == 0) {
                throw new ElasticSearchQueryException("No user found?");
            }
            else{
                return user.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void initAppUser(String username){
        if(user == null) {
            user = queryAppUser(username);
            user.getBookShelf().populateBookShelf(this.queryAllTextbooks());
        }
    }

    public void setAppUser(User u){
        user = u;
    }

    public void displayNotificationDialog(Context ctx, String title, String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton(ctx.getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public TextBook queryTextbook(String id){
        DataHelper.GetTextbookTask t = new DataHelper.GetTextbookTask();
        t.execute(id);
        try{
            ArrayList<TextBook> books = t.get();
            if(books.size() > 1){
                throw new ElasticSearchQueryException("Query book: "+id+" should only return one result but got "+books.size()+" \n");}
            else if(books.size() == 0){
                throw new ElasticSearchQueryException("No book found\n");
            }
            else{
                return books.get(0);
            }
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public ArrayList<TextBook> queryAllTextbooks(){
        DataHelper.GetAllTextbookTask t = new DataHelper.GetAllTextbookTask();
        t.execute(getAppUser().getName());
        try{
            ArrayList<TextBook> books = t.get();
            return books;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

}
