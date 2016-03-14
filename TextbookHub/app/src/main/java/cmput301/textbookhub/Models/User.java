package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

import io.searchbox.annotations.JestId;

/**
 * Created by Fred on 2016/3/8.
 */
public class User implements NamedItem, Syncable, DataBundleObject{

    public static final String BUNDLE_KEY_STR_USERNAME = "USERNAME";
    public static final String BUNDLE_KEY_STR_PASSWORD = "PASSWORD";
    public static final String BUNDLE_KEY_STR_EMAIL = "EMAIL";
    public static final String BUNDLE_KEY_LONG_TIMESTAMP = "USER_TIMESTAMP";

    private String username;
    private String password;
    private String email;
    private Long timestamp;

    private SyncStatus syncStatus = SyncStatus.SYNCED;

    private BookShelf bookShelf;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = new BookShelf();
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = new BookShelf();
    }

    public User(String username, String password, BookShelf bookShelf) {
        this.username = username;
        this.password = password;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        if(bookShelf != null) {
            this.bookShelf = bookShelf;
        }else{
            this.bookShelf = new BookShelf();
        }
    }

    public User(String username, String password, BookShelf bookShelf, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = bookShelf;
        if(bookShelf != null) {
            this.bookShelf = bookShelf;
        }else{
            this.bookShelf = new BookShelf();
        }
    }

    @JestId
    protected String jid;

    @Override
    public void onSync() {

    }

    @Override
    public void setSyncStatus(SyncStatus status) {
        this.syncStatus = status;
    }

    @Override
    public SyncStatus getSyncStatus() {
        return this.syncStatus;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public String getID() {
        return this.username;
    }

    @Override
    public void setID(String id) {
        this.username = id;
    }

    public BookShelf getBookShelf() {
        return bookShelf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null){
            return false;
        }
        if(o.getClass() != User.class){
            return false;
        }else{
            User user2 = (User) o;
            return this.username.equals(user2.getName());
        }
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public DataBundleLabel getDataModelLabel() {
        return DataBundleLabel.USER;
    }

    @Override
    public Bundle generateDataBundle() {
        Bundle b = new Bundle();
        b.putString(BUNDLE_KEY_STR_USERNAME, this.username);
        b.putString(BUNDLE_KEY_STR_PASSWORD, this.password);
        if(this.email != null)
            b.putString(BUNDLE_KEY_STR_EMAIL, this.email);
        b.putLong(BUNDLE_KEY_LONG_TIMESTAMP, this.timestamp);
        return b;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
