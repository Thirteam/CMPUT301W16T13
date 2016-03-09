package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable, UniqueItem<String>{


    private Double amount;
    private User bidder;
    private TextBook textBook;
    private Long timestamp;

    public Bid(Double amount, User user, TextBook textBook){
        this.amount = amount;
        this.bidder = user;
        this.textBook = textBook;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
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
        return this.amount.toString();
    }

    @Override
    public String getID() {
        return "BID_ID_" + this.bidder.getID() + " " + this.timestamp.toString();
    }
}
