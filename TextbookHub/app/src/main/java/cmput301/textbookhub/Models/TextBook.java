package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

import cmput301.textbookhub.Tools;
import io.searchbox.annotations.JestId;

/**
 * Created by Fred on 2016/3/8.
 */
public class TextBook implements NamedItem, Syncable, UniqueItem<String>{

    private String id;
    private String bookName;
    private String edition;
    private String category;
    private String comments;

    private String owner;
    private String borrower;

    private Long timestamp;

    private BidList bids;

    private BookStatus bookStatus = BookStatus.AVAILABLE;

    private SyncStatus syncStatus = SyncStatus.SYNCED;

    public TextBook(User owner, String bookName){
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.owner = owner.getName();
        this.bookName = bookName;
        this.id = owner.getID()+"_"+this.timestamp.toString();
        this.bids = new BidList();
    }

    @JestId
    protected String jid;

    @Override
    public void onSync() {

    }

    @Override
    public String getName() {
        return this.bookName;
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
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    @Override
    public void setSyncStatus(SyncStatus status) {
        this.syncStatus = syncStatus;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public String getBorrower() {
        return borrower;
    }

    public String getComments() {
        return comments;
    }

    public String getEdition() {
        return edition;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setBookBorrowed(User borrower){
        this.borrower = borrower.getName();
        this.bookStatus = BookStatus.BORROWED;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBookAvailable(){
        this.borrower = null;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setComments(String comments) {
        if(this.bookStatus != BookStatus.BORROWED)
            this.comments = comments;
    }

    public void setEdition(String edition) {
        if(this.bookStatus != BookStatus.BORROWED)
            this.edition = edition;
    }

    public void setBids(BidList bids) {
        if(this.bookStatus != BookStatus.BORROWED)
            this.bids = bids;
    }

    public void addBid(Bid bid){
        if(this.bookStatus != BookStatus.BORROWED)
            this.bids.addBid(bid);
    }

    public BidList getBidList() {
        return bids;
    }

    public boolean isUpdateAble(){
        return this.bookStatus != BookStatus.BORROWED;
    }

    public static class Builder{

        private TextBook textBook;

        public Builder(User owner, String name){
            this.textBook = new TextBook(owner, name);
        }

        public TextBook buildTextBook(){
            return this.textBook;
        }

        public Builder addComments(String comments){
            if(Tools.isStringValid(comments))
                this.textBook.setComments(comments);
            return this;
        }

        public Builder addEdition(String edition){
            if(Tools.isStringValid(edition))
                this.textBook.setEdition(edition);
            return this;
        }

        public Builder addCategory(String category){
            if(Tools.isStringValid(category))
                this.textBook.setCategory(category);
            return this;
        }

        public Builder addStartingBid(String amount, User user){
            this.textBook.addBid(new Bid(Double.parseDouble(amount), user));
            return this;
        }

    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
