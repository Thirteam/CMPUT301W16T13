package cmput301.textbookhub.Controllers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.TextBook;

/**
 * Created by Fred on 2016/3/10.
 */
public class MainActivityController extends BaseController {

    private static MainActivityController instance;

    private ArrayList<TextBook> searchResult;

    private MainActivityController() {
    }

    public static MainActivityController getInstance(String username){
        if(instance == null) {
            instance = new MainActivityController();
        }
        instance.initAppUser(username);
        return instance;
    }

    public ArrayList<TextBook> getCurrSearchResult(){
        if(this.searchResult == null){
            this.searchResult = new ArrayList<>();
        }
        return this.searchResult;
    }

    public void initNewSearch(String s){
        DataHelper.SearchTextbookTask t = new DataHelper.SearchTextbookTask();
        t.execute(s);
        ArrayList<TextBook> rv = new ArrayList();
        try{
            rv.addAll(t.get());
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.searchResult = rv;
    }
}
