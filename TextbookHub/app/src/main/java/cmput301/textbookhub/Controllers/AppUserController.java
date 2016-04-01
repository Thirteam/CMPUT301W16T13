package cmput301.textbookhub.Controllers;

import android.content.Context;

import java.util.ArrayList;

import cmput301.textbookhub.Models.OfflineCommandList;
import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.ElasticSearchQueryException;
import cmput301.textbookhub.Models.OfflineHelper;
import cmput301.textbookhub.Models.OfflineNewTextbookCommand;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;

/**
 * Created by Fred on 2016/3/16.
 *
 * Controller responsible for maintaining usr info throughout the life of the APP, uses singleton design pattern
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
        //fillUserBookShelf();
    }

    private void fillUserBookShelf(){
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
        getAppUser().getBookShelf().getAllBooks().add(book);
        if(hasInternetAccess(context)) {
            DataHelper.AddTextbookTask execute = new DataHelper.AddTextbookTask();
            execute.execute(book);
        }else{
            addNewOfflineBookCommand(new OfflineNewTextbookCommand(book));
            saveOfflineCommands();
        }

    }

    public void requestDeleteTextBook(Textbook book){
        getAppUser().getBookShelf().getAllBooks().remove(book);
        if(commandList.contains(book.getID())) {
            commandList.removeCommandByID(book.getID());
        }else{
            DataHelper.DeleteTextbookTask t = new DataHelper.DeleteTextbookTask();
            t.execute(book.getJid());
        }
    }
    public void editTextBook(Textbook book){
        //This function si nearly identical to the save but will remove the book from the list and re add it.
        getAppUser().getBookShelf().getAllBooks().remove(book);
        DataHelper.EditTextbookTask execute = new DataHelper.EditTextbookTask();
        execute.execute(book);
        getAppUser().getBookShelf().getAllBooks().add(book);
    }

    public ArrayList getAllPersonalBooks(){
        fillUserBookShelf();
        return getAppUser().getBookShelf().getAllBooks();
    }

    public ArrayList getAvailableBooks(){
        fillUserBookShelf();
        return getAppUser().getBookShelf().getAvailableBooks();
    }

    public ArrayList getBorrowedPersonalBooks(){
        fillUserBookShelf();
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
            throw new RuntimeException();
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

    @Override
    public void onInternetConnect() {
        executeOfflineCommands();
        fillUserBookShelf();
    }

    @Override
    public void onInternetDisconnect() {
        fillUserBookShelf();
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
