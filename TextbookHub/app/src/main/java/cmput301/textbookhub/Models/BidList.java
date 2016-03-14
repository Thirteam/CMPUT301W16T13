package cmput301.textbookhub.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class BidList {

    private ArrayList<Bid> bidList;

    public BidList(){
        this.bidList = new ArrayList<>();
    }

    public BidList(ArrayList bidList){
        this.bidList = bidList;
    }

    public ArrayList<Bid> getBids() {
        return bidList;
    }

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

    public void sortBidsByAmount(){
        Collections.sort(this.bidList, Collections.reverseOrder());
    }

    public Bid getHighestBid(){
        return this.bidList.get(0);
    }

    public ArrayList<String> getBidIDStringArray(){
        ArrayList<String> arr = new ArrayList<>();
        for(Bid b : this.bidList){
            arr.add(b.getID());
        }
        return arr;
    }

    public void addBid(Bid b){
        this.bidList.add(b);
        this.sortBidsByAmount();
    }
}
