package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class TextBook implements NamedItem, Syncable, DataBundleObject{

    public static String BUNDLE_KEY_STR_ID = "TEXTBOOK_ID";
    public static String BUNDLE_KEY_STR_BOOKNAME = "TEXTBOOK_NAME";
    public static String BUNDLE_KEY_STR_EDITION = "TEXTBOOK_EDITION";
    public static String BUNDLE_KEY_STR_COMMENTS = "TEXTBOOK_COMMENTS";
    public static String BUNDLE_KEY_STR_OWNER_ID = "TEXTBOOK_OWNER";
    public static String BUNDLE_KEY_STR_BORROWER_ID = "TEXTBOOK_BORROWED";
    public static String BUNDLE_KEY_LONG_TIMESTAMP = "TEXTBOOK_TIMESTAMP";
    public static String BUNDLE_KEY_STRARR_BIDLIST = "TEXTBOOK_BIDLIST";
    public static String BUNDLE_KEY_STR_BOOKSTAT = "TEXTBOOK_BOOKSTAT";

    private String id;
    private String bookName;
    private String edition;
    private String comments;

    private User owner;
    private User borrower;

    private Long timestamp;

    private BidList bids;

    private BookStatus bookStatus = BookStatus.AVAILABLE;

    private SyncStatus syncStatus = SyncStatus.SYNCED;

    public TextBook(User owner, String bookName){
        this.timestamp = Calendar.getInstance().getTimeInMillis();
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

    public void setBookBorrowed(User borrower){
        this.borrower = borrower;
        this.bookStatus = BookStatus.BORROWED;
    }

    public void setBookAvailable(){
        this.borrower = null;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setComments(String comments) {
        if(this.bookStatus == BookStatus.AVAILABLE)
            this.comments = comments;
    }

    public void setEdition(String edition) {
        if(this.bookStatus == BookStatus.AVAILABLE)
            this.edition = edition;
    }

    public void setBids(BidList bids) {
        if(this.bookStatus == BookStatus.AVAILABLE)
            this.bids = bids;
    }

    public BidList getBids() {
        return bids;
    }

    public boolean isUpdateAble(){
        return this.bookStatus == BookStatus.AVAILABLE;
    }

    @Override
    public Bundle generateDataBundle() {
        Bundle b = new Bundle();
        if(this.id != null)
            b.putString(BUNDLE_KEY_STR_ID, this.id);
        b.putString(BUNDLE_KEY_STR_BOOKNAME, this.bookName);
        b.putString(BUNDLE_KEY_STR_OWNER_ID, this.owner.getID());
        b.putString(BUNDLE_KEY_STR_BOOKSTAT, this.bookStatus.toString());
        b.putString(BUNDLE_KEY_STR_EDITION, this.edition);
        b.putString(BUNDLE_KEY_STR_COMMENTS, this.comments);
        b.putLong(BUNDLE_KEY_LONG_TIMESTAMP, this.timestamp);
        b.putStringArrayList(BUNDLE_KEY_STRARR_BIDLIST, this.bids.getBidIDStringArray());
        if(this.borrower != null)
            b.putString(BUNDLE_KEY_STR_BORROWER_ID, this.borrower.getID());
        return b;
    }

    @Override
    public DataBundleLabel getDataModelLabel() {
        return DataBundleLabel.TEXTBOOK;
    }
}
