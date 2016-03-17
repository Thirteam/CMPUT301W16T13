package cmput301.textbookhub.Controllers;

import java.util.ArrayList;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.TextBook;
import cmput301.textbookhub.Models.User;

/**
 * Created by Fred on 2016/3/17.
 *
 * Base controller class responsible for interacting with elastic search
 */
public abstract class BaseController {

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

    public ArrayList<TextBook> queryAllTextbooks(String username){
        DataHelper.GetAllTextbookTask t = new DataHelper.GetAllTextbookTask();
        t.execute(username);
        try{
            ArrayList<TextBook> books = t.get();
            return books;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

}
