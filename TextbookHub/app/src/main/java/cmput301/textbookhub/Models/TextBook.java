package cmput301.textbookhub.Models;

import java.util.Calendar;

import cmput301.textbookhub.Tools;
import io.searchbox.annotations.JestId;

/**
 * Created by Fred on 2016/3/8.
 */
public class Textbook implements NamedItem, UniqueItem<String>{

    private String id;
    private String bookName;
    private String edition;
    private String category;
    private String comments;

    private String owner;
    private String borrower;

    private String timestamp;

    private BidList bids;

    private BookStatus bookStatus = BookStatus.AVAILABLE;

    public Textbook(User owner, String bookName){
        this.timestamp = new Long(Calendar.getInstance().getTimeInMillis()).toString();
        this.owner = owner.getName();
        this.bookName = bookName;
        this.id = owner.getID()+"_BOOK_"+this.timestamp;
        this.bids = new BidList();
    }

    @JestId
    protected String jid;


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

        private Textbook textbook;

        public Builder(User owner, String name){
            this.textbook = new Textbook(owner, name);
        }

        public Textbook buildTextBook(){
            return this.textbook;
        }

        public Builder addComments(String comments){
            if(Tools.isStringValid(comments))
                this.textbook.setComments(comments);
            return this;
        }

        public Builder addEdition(String edition){
            if(Tools.isStringValid(edition))
                this.textbook.setEdition(edition);
            return this;
        }

        public Builder addCategory(String category){
            if(Tools.isStringValid(category))
                this.textbook.setCategory(category);
            return this;
        }

        public Builder addStartingBid(String amount, User user){
            this.textbook.addBid(new Bid(Double.parseDouble(amount), user));
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
