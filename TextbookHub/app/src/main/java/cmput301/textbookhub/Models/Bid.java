package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable, UniqueItem<String>{

    private String id;
    private Double amount;
    private User bidder;
    private TextBook textBook;
    private Long timestamp;

    public Bid(Double amount, User user){
        this.amount = amount;
        this.bidder = user;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.id = this.bidder.getID() + "_" + this.timestamp.toString();
    }

    public Bid(Double amount, User user, TextBook textBook){
        this.amount = amount;
        this.bidder = user;
        this.textBook = textBook;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.id = this.bidder.getID() + "_" + this.timestamp.toString();
    }

    public Double getAmount() {

        return amount;
    }

    public User getBidder() {
        return bidder;
    }

    public TextBook getTextBook() {
        return textBook;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Object another) {
        return this.amount.compareTo(((Bid) another).getAmount());
    }

    @Override
    public String toString() {
        return "$"+this.amount.toString();
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

}
