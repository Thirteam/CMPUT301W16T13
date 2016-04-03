package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;
import cmput301.textbookhub.Views.Activity_EditBook;

/**
 * Created by Fred on 2016/3/10.
 *
 * Each activity controller is dedicated to one activity
 */
public class ViewBookActivityController extends ActivityController {

    private Textbook textbook;

    public ViewBookActivityController() {}

    public void startEditBookActivity(Context ctx){
        Intent i = new Intent(ctx, Activity_EditBook.class);
        Bundle b = new Bundle();
        b.putString(Activity_EditBook.BUNDLE_KEY_BOOK_ID, this.textbook.getID());
        i.putExtra(Activity_EditBook.INTENT_EXTRAS_KEY_BUNDLE, b);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(i);
    }

    public void setCurrentBook(String id){
        this.textbook = queryTextbook(id);
    }

    public void setCurrentBook(Textbook book){
        this.textbook = book;
    }

    public Textbook getCurrentBook(){
        return this.textbook;
    }

    public boolean isBidValid(String bid) {

        try{
            Double b = Double.parseDouble(bid);
            b = new Double(Tools.roundDecimal(2, b));
            if (this.textbook.getBidList().getHighestBid().getAmount() < b) {
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBidderValid(){
        if(this.textbook.getOwner().equals(this.textbook.getBidList().getHighestBid().getBidder())){
            return false;
        }
        return true;
    }

    public void addNewValidBid(Bid b){
        this.textbook.addBid(b);
        this.textbook.setBookStatus(BookStatus.BIDDED);
    }

    private void clearAndResetBids(){
        this.textbook.clearAllBids();
        this.textbook.addBid(new Bid(0.0, queryUser(this.textbook.getOwner())));
    }

    public User getOwnerInfo(){
        return queryUser(this.textbook.getOwner());
    }

    public void setBookBorrowed(){
        this.textbook.setBookBorrowed(this.textbook.getBidList().getHighestBid().getBidder());
        this.clearAndResetBids();
    }

    public void setBookReturned(){
        this.textbook.setBookReturned();
    }

    public void updateCurrentTextbook(){
        updateTextbookOnServer(this.textbook);
    }
}
