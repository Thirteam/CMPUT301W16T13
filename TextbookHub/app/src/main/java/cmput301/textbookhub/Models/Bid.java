package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * <code>Bid</code> is use to return the bid amount, bidder, bid time made from <code>User</code> on a <code>Textbook</code>.
 * 
 * @author Thirteam
 * @version 1.1
 * @since 2016/03/08
 * @see User
 * @see Testbook
 * Created by Fred on 2016/3/8.
 */
public class Bid implements Comparable, UniqueItem<String>{

    private String id;
    private Double amount;
    private String bidder;
    private String timestamp;

    /**
     * Create a new <code>Bid</code> by the user.
     * 
     * @param amount the amount bidded
     * @param user the user bid on the textbook
     * */
    public Bid(Double amount, User user){
        this.amount = amount;
        this.bidder = user.getName();
        this.timestamp = new Long(Calendar.getInstance().getTimeInMillis()).toString();
        this.id = user.getID() + "_BID_" + this.timestamp;
    }

    /**
     * Return the amount bidded.
     * 
     * @retrun the bid amount
     */
    public Double getAmount() {

        return amount;
    }

    /**
     * Return the bidder on the textbook.
     * 
     * @return the textbook bidder
     */
    public String getBidder() {
        return bidder;
    }

    /**
     * Return the bid time.
     * 
     * @retrun the timestamp of the bid
     */
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
