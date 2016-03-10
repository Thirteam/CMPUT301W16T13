package cmput301.textbookhub.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Fred on 2016/3/8.
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

    public void sortInMostRecentOrder(){
        Collections.sort(this.bidList, new Comparator<Bid>(){
            @Override
            public int compare(Bid lhs, Bid rhs) {
                return rhs.getTimestamp().compareTo(lhs.getTimestamp());
            }
        });
    }

    public void sortBidsByAmount(){
        Collections.sort(this.bidList, Collections.reverseOrder());
    }

    public Bid getHighestBid() throws IndexOutOfBoundsException{
        if(this.bidList.size() != 0){
            this.sortBidsByAmount();
            return this.bidList.get(0);
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    public ArrayList<String> getBidIDStringArray(){
        ArrayList<String> arr = new ArrayList<>();
        for(Bid b : this.bidList){
            arr.add(b.getID());
        }
        return arr;
    }
}
