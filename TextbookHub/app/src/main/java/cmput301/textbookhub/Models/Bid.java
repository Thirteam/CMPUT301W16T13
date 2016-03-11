package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable, DataBundleObject{

    public static final String BUNDLE_KEY_STR_ID = "BID_ID";
    public static final String BUNDLE_KEY_DOUBLE_AMOUNT = "BID_AMOUNT";
    public static final String BUNDLE_KEY_STR_BIDDER_ID = "BID_BIDDER";
    public static final String BUNDLE_KEY_STR_TEXTBOOK_ID = "BID_TEXTBOOK";
    public static final String BUNDLE_KEY_LONG_TIMESTAMP = "BID_TIMESTAMP";

    private String id;
    private Double amount;
    private User bidder;
    private TextBook textBook;
    private Long timestamp;

    public Bid(Double amount, User user, TextBook textBook){
        this.amount = amount;
        this.bidder = user;
        this.textBook = textBook;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.id = this.bidder.getID() + "_" + this.timestamp.toString();
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {

        return amount;
    }

    public User getBidder() {
        return bidder;
    }

    public TextBook getTextBook() {
        return this.textBook;
    }

    @Override
    public int compareTo(Object another) {
        Bid other;
        try{
            other = (Bid) another;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return this.getAmount().compareTo(other.getAmount());
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return this.getAmount().toString();
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public Bundle generateDataBundle() {
        Bundle b = new Bundle();
        b.putString(BUNDLE_KEY_STR_ID, this.id);
        b.putDouble(BUNDLE_KEY_DOUBLE_AMOUNT, this.getAmount());
        b.putString(BUNDLE_KEY_STR_BIDDER_ID, this.bidder.getID());
        b.putString(BUNDLE_KEY_STR_TEXTBOOK_ID, this.textBook.getID());
        b.putLong(BUNDLE_KEY_LONG_TIMESTAMP, this.timestamp);
        return b;
    }

    @Override
    public DataBundleLabel getDataModelLabel() {
        return DataBundleLabel.BID;
    }
}
