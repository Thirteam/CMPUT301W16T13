package cmput301.textbookhub.Controllers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.Textbook;

/**
 * Created by Fred on 2016/3/10.
 */
public class MyBidsActivityController extends ActivityController {

    public MyBidsActivityController() {}

    public ArrayList<Textbook> getBooksIBiddedOn(String me){
        DataHelper.GetBiddedTextbookByBidderTask t  = new DataHelper.GetBiddedTextbookByBidderTask();
        ArrayList<Textbook> rv = new ArrayList<>();
        t.execute(me);
        try{
           rv.addAll(t.get());
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return rv;
    }

}
