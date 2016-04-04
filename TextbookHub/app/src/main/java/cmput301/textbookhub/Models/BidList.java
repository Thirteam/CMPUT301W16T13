package cmput301.textbookhub.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <code>BidList</code> used to keep a list of bids which are associated with a <code>Textbook</code>.
 *
 * @author Thirteam
 * @version 1.1
 * @since 2016/03/08
 * @see Textbook
 * @see Bid
 *
 * Created by Fred on 2016/3/8.
 */
public class BidList {

    private ArrayList<Bid> bidList;

    public BidList(){
        this.bidList = new ArrayList<>();
    }

    /**
     * Gets all <code>Bid</code>(s) from the <code>BidList</code>
     *
     * @return the <code>BidList</code>.
     * */
    public ArrayList<Bid> getBids() {
        return bidList;
    }

    /**
     * Sorts the <code>BidList</code> in the most recent order
     *
     * @return the sorted <code>BidList</code>.
     * */
    public ArrayList<Bid> sortInMostRecentOrder(){
        ArrayList rv = new ArrayList<Bid>();
        rv.addAll(this.bidList);
        Collections.sort(rv, new Comparator<Bid>(){
            @Override
            public int compare(Bid lhs, Bid rhs) {
                return rhs.getTimestamp().compareTo(lhs.getTimestamp());
            }
        });
        return rv;
    }

    /**
     * Sorts the <code>BidList</code> by the largest monetary amount
     * */
    public void sortBidsByAmount(){
        Collections.sort(this.bidList, Collections.reverseOrder());
    }

    /**
     * Gets the highest <code>Bid</code> from the <code>BidList</code>
     *
     * @return the largest <code>Bid</code>.
     * */
    public Bid getHighestBid(){
        return this.bidList.get(0);
    }

    /**
     * Gets the <code>Bid</code> ID
     *
     * @return the <code>Bid</code> ID.
     * */
    public ArrayList<String> getBidIDStringArray(){
        ArrayList<String> arr = new ArrayList<>();
        for(Bid b : this.bidList){
            arr.add(b.getID());
        }
        return arr;
    }

    /**
     * Adds the <code>Bid</code> to the <code>BidList</code>
     *
     * @param b the <code>Bid</code> to be added.
     * */
    public void addBid(Bid b){
        this.bidList.add(b);
        this.sortBidsByAmount();
    }

    /**
     * Removes all <code>Bid</code>(s) from the <code>BidList</code>.
     * */
    public void clearAllBids(){
        this.bidList.clear();
    }
}
