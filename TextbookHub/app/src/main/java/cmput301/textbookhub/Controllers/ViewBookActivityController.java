package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.R;
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

    private boolean isBidValid(String bid){
        Double b = Double.parseDouble(bid);
        if(this.textbook.getBidList().getHighestBid().getAmount() < b){
            return true;
        }
        return false;
    }

    public void addNewBid(Context ctx, String bid, User u){
        if(isBidValid(bid)){
            Double new_bid = Double.parseDouble(bid);
            this.textbook.getBidList().addBid(new Bid(new_bid, u));
            //TODO:update the bid

        }else{
            displayNotificationDialog(ctx, ctx.getResources().getString(R.string.error), ctx.getResources().getString(R.string.invalid_bid_entered));
        }
    }
}
