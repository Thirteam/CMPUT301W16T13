package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import cmput301.textbookhub.GPSTracker;
import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.OfflineCommandList;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.OfflineHelper;
import cmput301.textbookhub.Models.OfflineNewTextbookCommand;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;
import cmput301.textbookhub.Tools;

/**
 * <code>AppUserController</code> responsible for maintaining usr info throughout the life of the APP, uses singleton design pattern
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/10
 * @see BaseController
 *
 * Created by Fred on 2016/3/16.
 */
public class AppUserController extends BaseController implements NetworkStateObserver{

    private static AppUserController instance;
    private User user;
    private OfflineHelper offlineHelper = new OfflineHelper();
    private Context context;

    private OfflineCommandList commandList = new OfflineCommandList();

    private AppUserController(){
        NetworkStateManager.getInstance().addControllerObserver(this);
    }

    public void unRegisterObserver(){
        NetworkStateManager.getInstance().removeViewObserver(this);
    }

    public static AppUserController getInstance(){
        if(instance == null)
            instance = new AppUserController();
        return instance;
    }

    public void setAppContext(Context ctx){
        this.context = ctx;
    }

    public void setAppUser(User u){
        this.user = u;
        executeOfflineCommands();
        //loadUserBookShelf();
    }

    public void loadUserBookShelf(){
        if(hasInternetAccess(context)) {
            user.getBookShelf().populateBookShelf(this.queryAllTextbooks(user.getName()));
        }else{
            ArrayList list = getOfflineBookList(user.getName());
            user.getBookShelf().populateBookShelf(list);
        }
    }

    public User getAppUser(){
        return user;
    }

    public void addNewOfflineBookCommand(OfflineNewTextbookCommand c){
        this.commandList.addOfflineBookCommand(c);
    }

    public void clearAppUser(){
        user = null;
    }

    public void saveTextBook(Textbook book){
        getAppUser().getBookShelf().addNewTextBook(book);
        if(hasInternetAccess(context)) {
            DataHelper.AddTextbookTask execute = new DataHelper.AddTextbookTask();
            execute.execute(book);
        }else{
            addNewOfflineBookCommand(new OfflineNewTextbookCommand(book));
            saveOfflineCommands();
        }

    }

    public void requestDeleteTextBook(Textbook book){
        getAppUser().getBookShelf().removeBook(book);
        if(commandList.contains(book.getID())) {
            commandList.removeCommandByID(book.getID());
            saveOfflineCommands();
        }else{
            DataHelper.DeleteTextbookTask t = new DataHelper.DeleteTextbookTask();
            t.execute(book.getJid());
        }
    }
    public void updateExistingPersonalTextbook(Textbook book){
        //This function is nearly identical to the save but will remove the book from the list and re add it.
        getAppUser().getBookShelf().removeBook(book);
        clearAndResetBids(book);
        if(hasInternetAccess(context)) {
            updateTextbookOnServer(book);
        }else{
            commandList.updateCommandByID(book.getID(), new OfflineNewTextbookCommand(book));
        }
        getAppUser().getBookShelf().addNewTextBook(book);
    }

    public ArrayList getAllPersonalBooks(){
        //loadUserBookShelf();
        return getAppUser().getBookShelf().getAllBooks();
    }

    public ArrayList getAvailableBooks(){
        //loadUserBookShelf();
        return getAppUser().getBookShelf().getAvailableBooks();
    }

    public ArrayList getBorrowedPersonalBooks(){
        //loadUserBookShelf();
        return getAppUser().getBookShelf().getBorrowedBooks();
    }

    public boolean registerNewUser(String username, String password, String email){
        if (isUsernameAvailable(username)) {
            User u = new User(username, password, email);
            setAppUser(u);
            DataHelper.AddUserTask t = new DataHelper.AddUserTask();
            t.execute(u);
            return true;
        }
        return false;
    }

    public void updateExistingUser(String password, String email){
        getAppUser().setPassword(password);
        getAppUser().setEmail(email);
        //make sure to update the local copy as well
        this.offlineHelper.saveUserToFile(context, getAppUser());
        if(hasInternetAccess(context)) {
            DataHelper.UpdateUserTask t = new DataHelper.UpdateUserTask();
            t.execute(getAppUser());
        }else{
            this.commandList.setUpdateUserFlag();
        }

    }

    public boolean newBidsOnMyBooks(){
        this.loadUserBookShelf();
        return this.getAppUser().getBookShelf().someBookHasNewBids();
    }

    public boolean userAuthSuccess(String username, String password){
        DataHelper.GetUserTask t = new DataHelper.GetUserTask();
        t.execute(username);
        try{
            ArrayList<User> user = t.get();
            if(user.size() > 1){
                throw new ElasticSearchQueryException("Query username: "+username+" should only return one result but got "+user.size()+" \n");}
            else if(user.size() == 0) {
                return false;
            }
            else{
                if(user.get(0).getPassword().equals(password)) {
                    this.setAppUser(user.get(0));
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameAvailable(String username){
        DataHelper.GetUserTask t = new DataHelper.GetUserTask();
        t.execute(username);
        try{
            ArrayList<User> user = t.get();
            if(user.size() > 1){
                throw new ElasticSearchQueryException("Query username: "+username+" should only return one result but got "+user.size()+" \n");}
            return user.size() == 0;
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    public void loadOfflineCommands(){
        this.commandList = this.offlineHelper.loadCommandsFromFile(context);
    }

    public void saveOfflineCommands(){
        this.offlineHelper.saveCommandsToFile(context, commandList);
    }

    public User getOfflineUserProfile() throws NoOfflineUserProfileFoundException{
        User u = this.offlineHelper.loadUserFromFile(context);
        if(u != null)
            return u;
        throw new NoOfflineUserProfileFoundException();
    }

    public void saveOfflineUserProfile(){
        this.offlineHelper.saveUserToFile(context, getAppUser());
    }

    public Textbook getOfflineBookByID(String id){
        return this.commandList.getCommandByID(id).getRelatedBook();
    }

    @Override
    public void onInternetConnect() {
        if(getAppUser()!= null) {
            executeOfflineCommands();
            loadUserBookShelf();
        }
    }

    @Override
    public void onInternetDisconnect() {
        if(getAppUser() != null) {
            loadUserBookShelf();
        }
    }

    public LatLng getCurrUserLocation(){
        GPSTracker gps = new GPSTracker(context);
        Double lat = 53.631611;
        Double lon = -113.323975;
        if(gps.canGetLocation()){
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        }else {
            Log.i("GPS FAILURE", "CANNOT GET LOC");
        }
        gps.stopUsingGPS();
        return new LatLng(lat, lon);
    }

    public void executeOfflineCommands(){
        if(hasInternetAccess(context)) {
            if (this.commandList.hasPendingCommands()) {
                this.commandList.uploadAllOfflineBooks();
                saveOfflineCommands();
            }
            if(this.commandList.needToUpdateUser()){
                this.updateExistingUser(getAppUser().getPassword(), getAppUser().getEmail());
                this.commandList.clearUpdateUserFlag();
            }
        }
    }

    public ArrayList<Textbook> getBooksAroundMe(){
        DataHelper.SearchTextbookTask t = new DataHelper.SearchTextbookTask();
        t.execute("");
        LatLng loc = getCurrUserLocation();
        try{
            ArrayList<Textbook> tmp = t.get();
            ArrayList<Textbook> rv = new ArrayList<>();
            final HashMap<Textbook, Double> map = new HashMap<>();
            if(tmp.size() == 0)
                Log.i("EMPTY RV", "NO BOOK AROUND?");
            for(Textbook b : tmp){
                Double dist = Tools.calculateDistanceInMeters(b.getLat(),b.getLon(),loc.getLatitude(), loc.getLongitude());
                if(dist<10000.0) {
                    if (!b.getOwner().equals(getAppUser().getName()) && !b.getBookStatus().equals(BookStatus.BORROWED)) {
                        rv.add(b);
                        map.put(b, dist);
                    }
                }
            }
            Collections.sort(rv, new Comparator<Textbook>() {
                @Override
                public int compare(Textbook lhs, Textbook rhs) {
                    return map.get(lhs).compareTo(map.get(rhs));
                }
            });
            return rv;
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private ArrayList<Textbook> getOfflineBookList(String username){
        ArrayList<Textbook> rv = new ArrayList();
        for(OfflineNewTextbookCommand c:commandList.getBookCommands()){
            if(c.getRelatedBook().getOwner().equals(username)){
                rv.add(c.getRelatedBook());
            }
        }
        return rv;
    }

    public static class NoOfflineUserProfileFoundException extends Exception{}

}
