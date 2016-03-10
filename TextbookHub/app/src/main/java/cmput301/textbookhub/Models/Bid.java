package cmput301.textbookhub.Models;

<<<<<<< HEAD
=======
import android.os.Bundle;

>>>>>>> xuefei1
import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
<<<<<<< HEAD
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
=======
public class Bid implements Comparable, DataBundleObject{

    public static String BUNDLE_KEY_STR_ID = "BID_ID";
    public static String BUNDLE_KEY_DOUBLE_AMOUNT = "BID_AMOUNT";
    public static String BUNDLE_KEY_STR_BIDDER_ID = "BID_BIDDER";
    public static String BUNDLE_KEY_STR_TEXTBOOK_ID = "BID_TEXTBOOK";
    public static String BUNDLE_KEY_LONG_TIMESTAMP = "BID_TIMESTAMP";

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
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {

>>>>>>> xuefei1
        return amount;
    }

    public User getBidder() {
        return bidder;
    }

    public TextBook getTextBook() {
<<<<<<< HEAD
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
=======
        return textBook;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Object another) {
        return this.amount.compareTo(((Bid) another).getAmount());
>>>>>>> xuefei1
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return this.getAmount().toString();
    }
=======
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

    @Override
    public Bundle generateDataBundle() {
        Bundle b = new Bundle();
        if(this.id != null)
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

>>>>>>> xuefei1
}
