package cmput301.textbookhub.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import cmput301.textbookhub.Tools;
import io.searchbox.annotations.JestId;

/**
 * <code>Textbook</code> is the item user own or borrowed. It is can also be bid on.
 * Textbooks can be find by searching through the database.
 *
 * @author Thirteam
 * @version 1.1
 * @since 2016/03/08
 * @see BidList
 * @see BookShelf
 * @see BookStatus
 *
 * Created by Fred on 2016/3/8.
 */
public class Textbook implements NamedItem, UniqueItem<String>{

    private String id;
    private String bookName;
    private String edition;
    private String category;
    private String comments;

    private String start_bid_amount;

    private String owner;
    private String borrower = "";

    private String timestamp;

    private BidList bids;

    private ArrayList<String> pictures = new ArrayList<>();

    private BookStatus bookStatus = BookStatus.AVAILABLE;

    private ViewStatus viewStatus = ViewStatus.NO_NEW_BID;

    private Double lat = 53.631611;

    private Double lon = -113.323975;

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

    public void setBookBorrowed(String borrower){
        this.borrower = borrower;
        this.bookStatus = BookStatus.BORROWED;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void addBid(Bid bid){
        this.bids.addBid(bid);
    }

    public void clearAllBids(){
        this.bids.clearAllBids();
    }

    public void setStartingBidAmount(String bid){
        this.start_bid_amount = bid;
    }

    public String getStartBidAmount() {
        return start_bid_amount;
    }

    public void setBookReturned(){
        this.borrower = "";
        this.bookStatus = BookStatus.AVAILABLE;
        this.viewStatus = ViewStatus.NO_NEW_BID;
    }

    public boolean hasValidBids(){
        return this.bids.getBids().size() > 1;
    }

    public void setBookStatus(BookStatus status){
        this.bookStatus = status;
    }

    public BidList getBidList() {
        return bids;
    }

    /**
     * <code>getBookHighestBidAmount</code> gets the highest <code>Bid</code> for this <code>Textbook</code>.
     *
     * @return highest monetary <code>Bid</code> amount.
     */
    public Double getBookHighestBidAmount(){
        return bids.getHighestBid().getAmount();
    }

    /**
     * The builder is used to build a <code>Textbook</code>
     *
     * <p>This aids in the building of </p>
     */
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

        public Builder addPictures(ArrayList<Bitmap> pics){
            for(Bitmap p:pics)
                this.textbook.addPicture(p);
            return this;
        }

        public Builder addCategory(String category){
            if(Tools.isStringValid(category))
                this.textbook.setCategory(category);
            return this;
        }

        public Builder addLat(Double lat){
            this.textbook.setLat(lat);
            return this;
        }

        public Builder addLon(Double lon){
            this.textbook.setLon(lon);
            return this;
        }

        public Builder addStartingBid(String amount, User user){
            try{
                Double.parseDouble(amount);
            }catch (Exception e){
                amount = "0.0";
            }
            this.textbook.addBid(new Bid(Double.parseDouble(amount), user));
            this.textbook.setStartingBidAmount(amount);
            return this;
        }

    }

<<<<<<< HEAD
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

=======
    /**
     * <code>getPictures</code> returns the pictures for the specified <code>Textbook</code>
     *
     * @return an ArrayList of pictures
     */
>>>>>>> a1c81148f4f58e28a2b0cf4421ab915891569b53
    public ArrayList<Bitmap>getPictures(){
        ArrayList<Bitmap> rv = new ArrayList<>();
        for(String pic:this.pictures){
            rv.add(convertStringToBitmap(pic));
        }
        return rv;
    }

<<<<<<< HEAD
    public void clearAllPictures(){
        this.pictures.clear();
    }

=======
    /**
     * <code>addPicture</code> adds a picture to the <code>Textbook</code>
     *
     * @param pic the picture to be added
     */
>>>>>>> a1c81148f4f58e28a2b0cf4421ab915891569b53
    public void addPicture(Bitmap pic){
        String str = convertBitmapToString(pic);
        this.pictures.add(str);
    }

<<<<<<< HEAD
=======
    /**
     * <code>removePictureAt</code> removes the picture from the <code>Textbook</code> at the specified index
     *
     * @param idx the index of the picture to be removed from this <code>Textbook</code>
     */
    public void removePictureAt(int idx){
        this.pictures.remove(idx);
    }

    /**
     * <code>convertStringToBitmap</code> converts the <code>String</code> to bitmap format
     *
     * @param pic the <code>String</code> to be converted
     * @return the bitmap result
     */
>>>>>>> a1c81148f4f58e28a2b0cf4421ab915891569b53
    private Bitmap convertStringToBitmap(String pic){
        byte[] decodeString = Base64.decode(pic, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
    }

    /**
     * <code>convertBitmapToString</code> converts the bitmap format to the <code>String</code> format
     *
     * @param picture the bitmap to converted
     * @return the resulting <code>String</code>
     */
    public String convertBitmapToString(Bitmap picture){
        int bitmapByteCount= BitmapCompat.getAllocationByteCount(picture);
        Log.i("SIZE IN BYTES BEFORE", ""+bitmapByteCount);
        picture = Tools.getResizedBitmap(picture, 190);
        bitmapByteCount= BitmapCompat.getAllocationByteCount(picture);
        Log.i("SIZE IN BYTES AFTER", ""+bitmapByteCount);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        Log.i("SIZE IN BYTES FINAL", "" + byteArrayOutputStream.size());
        byte[] b = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public ViewStatus getViewStatus() {
        return this.viewStatus;
    }

    public void flagValidNewBidAdded() {
        this.viewStatus = ViewStatus.HAS_NEW_BID;
    }

    public void flagAllBidsViewed() {
        this.viewStatus = ViewStatus.NO_NEW_BID;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
