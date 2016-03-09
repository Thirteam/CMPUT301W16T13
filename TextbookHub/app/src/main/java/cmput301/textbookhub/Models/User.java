package cmput301.textbookhub.Models;

import java.util.Calendar;

/**
 * Created by Fred on 2016/3/8.
 */
public class User implements NamedItem, UniqueItem<String>, Syncable{

    private String username;
    private String password;
    private String email;
    private Long timestamp;

    private SyncStatus syncStatus = SyncStatus.SYNCED;

    private BookShelf bookShelf;

    public User(String username, String password, BookShelf bookShelf) {
        this.username = username;
        this.password = password;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = bookShelf;
    }

    public User(String username, String password, BookShelf bookShelf, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = bookShelf;
    }

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
}
