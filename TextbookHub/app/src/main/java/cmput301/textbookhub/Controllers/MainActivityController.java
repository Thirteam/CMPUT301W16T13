package cmput301.textbookhub.Controllers;

import java.util.ArrayList;
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

    public ArrayList initNewSearch(String s){
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
        return this.searchResult;
    }
}
