package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;

/**
 * Created by Fred on 2016/3/17.
 *
 * Base controller class responsible for interacting with elastic search
 */
public abstract class BaseController {

    public Textbook queryTextbook(String id){
        DataHelper.GetTextbookTask t = new DataHelper.GetTextbookTask();
        t.execute(id);
        try{
            ArrayList<Textbook> books = t.get();
            if(books.size() > 1){
                throw new ElasticSearchQueryException("Query book: "+id+" should only return one result but got "+books.size()+" \n");}
            else if(books.size() == 0){
                throw new ElasticSearchQueryException("No book found\n");
            }
            else{
                return books.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public ArrayList<Textbook> queryAllTextbooks(String username){
        DataHelper.GetAllTextbookTask t = new DataHelper.GetAllTextbookTask();
        t.execute(username);
        try{
            ArrayList<Textbook> books = t.get();
            return books;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User queryUser(String username){
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

    public boolean hasInternetAccess(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Warning: this method could take a long time to return, use with great caution
    public boolean hasServerAccess(){
        DataHelper.TestServerConnectionTask t = new DataHelper.TestServerConnectionTask();
        t.execute();
        try{
            return t.get();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void updateTextbookOnServer(Textbook b){
        DataHelper.UpdateTextbookTask execute = new DataHelper.UpdateTextbookTask();
        execute.execute(b);
    }

    public void clearAndResetBids(Textbook b){
        b.clearAllBids();
        b.addBid(new Bid(Double.parseDouble(b.getStartBidAmount()), queryUser(b.getOwner())));
    }

}
