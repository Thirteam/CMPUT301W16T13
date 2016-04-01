package cmput301.textbookhub.Controllers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.Textbook;

/**
 * Created by Fred on 2016/3/10.
 */
public class MainActivityController extends ActivityController {

    private ArrayList<Textbook> searchResult;

    public MainActivityController() {}

    public ArrayList<Textbook> getCurrSearchResult(){
        if(this.searchResult == null){
            this.searchResult = new ArrayList<>();
        }
        return this.searchResult;
    }

    public ArrayList initNewSearch(String s){
        DataHelper.SearchTextbookTask t = new DataHelper.SearchTextbookTask();
        t.execute(s);
        ArrayList<Textbook> rv = new ArrayList();
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
