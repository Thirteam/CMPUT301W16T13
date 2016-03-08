package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable{

    private Double amount;
    private User bidder;
    private TextBook item;
    private long timestamp;

    public Bid(double amount, User bidder, TextBook item){
        Calendar c = Calendar.getInstance();
        this.timestamp = c.getTimeInMillis();
        this.amount = amount;
        this.bidder = bidder;
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public User getBidder() {
        return bidder;
    }

    public TextBook getTextBook() {
        return item;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return this.getAmount().toString();
    }
}
