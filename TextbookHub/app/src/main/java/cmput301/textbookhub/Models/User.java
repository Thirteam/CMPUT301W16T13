package cmput301.textbookhub.Models;

import android.os.Bundle;

import java.util.Calendar;

import cmput301.textbookhub.Views.Activity_Login;
import cmput301.textbookhub.Views.Activity_UserProfile;
import cmput301.textbookhub.Views.MyBidListAdapter;
import io.searchbox.annotations.JestId;

/**
 * <code>User</code> is the user of the application, and contains lists of <code>Textbook</code>.
 * User has own and borrow textbooks. User can also bid on textbooks.
 * 
 * @author Thirteam
 * @version 1.1
 * @since 2016/03/08
 * @see Activity_UserProfile
 * @see MyBidListAdapter
 * @see Activity_Login
 *
 * Created by Fred on 2016/3/8.
 */
public class User implements NamedItem, UniqueItem<String>{

    private String username;
    private String password;
    private String email;
    private Long timestamp;

    private BookShelf bookShelf;

    /**
     * <code>User</code> with unique username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     */ 
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
        this.bookShelf = new BookShelf();
    }

    /**
     * <code>User</code> with a unique username, password, and an email.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     */ 
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

    /**
     * Return the user email.
     * 
     * @return user email
     */ 
    public String getEmail() {
        return email;
    }

    /**
     * Return the user password.
     * 
     * @return user password
     */ 
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

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
}
