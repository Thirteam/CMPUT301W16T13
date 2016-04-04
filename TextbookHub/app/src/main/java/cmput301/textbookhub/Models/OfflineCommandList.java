package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * <code>OfflineCommandList</code> is used to handle offline behaviour.
 * <p>The commands are added to a queue to be executed when the user regains internet connection.</p>
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/30
 * @see OfflineHelper
 * @see OfflineNewTextbookCommand
 *
 * Created by Fred on 2016/3/30.
 */
public class OfflineCommandList {

    private boolean updateUser = false;

    public ArrayList<OfflineNewTextbookCommand> bookCommands;

    public OfflineCommandList(){
        this.bookCommands = new ArrayList<>();
    }

    /**
     * <code>uploadAllOfflineBooks</code> when executed will push all offline changes to the server.
     *
     * */
    public void uploadAllOfflineBooks(){
        for(OfflineNewTextbookCommand c:this.bookCommands){
            c.execute();
        }
        clearAllCommands();
    }

    /**
     * <code>addOfflineBooks</code> adds a <code>Textbook</code> change/add task to the <code>OfflineCommandList</code>
     *
     * @param c the list to add the command to
     * */
    public void addOfflineBookCommand(OfflineNewTextbookCommand c){
        if(contains(c.getCommandID())) {
            this.updateCommandByID(c.getCommandID(), c);
        }else{
            this.bookCommands.add(c);
        }
    }

    /**
     * <code>OfflineNewTextbookCommand</code> adds a <code>Textbook</code> New task to the <code>OfflineCommandList</code>
     *
     * @param id the id of the command
     * @return <code>null</code>
     * */
    public OfflineNewTextbookCommand getCommandByID(String id){
        for(OfflineNewTextbookCommand c:this.bookCommands){
            if(c.getCommandID().equals(id))
                return c;
        }
        return null;
    }

    /**
     * <code>removeCommandByID</code> removes a task to be executed from the <code>OfflineCommandList</code>
     *
     * @param id the if of the command
     * */
    public void removeCommandByID(String id){
        for(int i = 0; i < this.bookCommands.size(); i++){
            if(this.bookCommands.get(i).getCommandID().equals(id)) {
                this.bookCommands.remove(i);
                break;
            }
        }
    }

    /**
     * <code>updateCommandByID</code> removes a task to be executed from the <code>OfflineCommandList</code>
     *
     * @param c the list to add the command to
     * */
    public void updateCommandByID(String id, OfflineNewTextbookCommand c){
        removeCommandByID(id);
        addOfflineBookCommand(c);
    }

    /**
     * <code>clearAllCommands</code> removes all task(s) to be executed from the <code>OfflineCommandList</code>
    * */
    public void clearAllCommands(){
        this.bookCommands.clear();
    }

    public boolean hasPendingCommands(){
        return this.bookCommands.size() != 0;
    }

    public boolean contains(String id){
        return this.getCommandByID(id) != null;
    }

    public void setUpdateUserFlag(){
        this.updateUser = true;
    }

    public ArrayList<OfflineNewTextbookCommand> getBookCommands() {
        return bookCommands;
    }

    public void clearUpdateUserFlag(){
        this.updateUser = false;
    }

    public boolean needToUpdateUser(){
        return this.updateUser;
    }
}
