package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class TextBook implements NamedItem, UniqueItem<String>, Syncable{

    private String id;
    private String bookName;
    private String edition;
    private String comments;

    private User owner;
    private User borrower;

    private Long timestamp;

    private BidList bids;

    private BookStatus bookStatus = BookStatus.PENDING;

    private SyncStatus syncStatus = SyncStatus.SYNCED;

    public TextBook(User owner, String bookName){
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.id = "BOOK_ID_" + owner.getID() + " " + this.timestamp.toString();
        this.owner = owner;
        this.bookName = bookName;
    }

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

    public User getBorrower() {
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

    public User getOwner() {
        return owner;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setBids(BidList bids) {
        this.bids = bids;
    }

    public BidList getBids() {

        return bids;
    }
}
