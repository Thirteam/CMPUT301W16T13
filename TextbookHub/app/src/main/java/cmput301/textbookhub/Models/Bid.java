package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable, UniqueItem<String>{

    private String id;
    private Double amount;
    private String bidder;
    private String timestamp;

    public Bid(Double amount, User user){
        this.amount = amount;
        this.bidder = user.getName();
        this.timestamp = new Long(Calendar.getInstance().getTimeInMillis()).toString();
        this.id = user.getID() + "_BID_" + this.timestamp;
    }

    public Double getAmount() {

        return amount;
    }

    public String getBidder() {
        return bidder;
    }

    public String getTimestamp() {
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
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

}
