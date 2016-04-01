package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * Created by Fred on 2016/3/30.
 */
public class OfflineCommandList {

    private boolean updateUser = false;

    public ArrayList<OfflineNewTextbookCommand> bookCommands;

    public OfflineCommandList(){
        this.bookCommands = new ArrayList<>();
    }

    public void uploadAllOfflineBooks(){
        for(OfflineNewTextbookCommand c:this.bookCommands){
            c.execute();
        }
        clearAllCommands();
    }

    public void addOfflineBookCommand(OfflineNewTextbookCommand c){
        if(contains(c.getCommandID())) {
            this.updateCommandByID(c.getCommandID(), c);
        }else{
            this.bookCommands.add(c);
        }
    }

    public OfflineNewTextbookCommand getCommandByID(String id){
        for(OfflineNewTextbookCommand c:this.bookCommands){
            if(c.getCommandID().equals(id))
                return c;
        }
        return null;
    }

    public void removeCommandByID(String id){
        for(int i = 0; i < this.bookCommands.size(); i++){
            if(this.bookCommands.get(i).getCommandID().equals(id)) {
                this.bookCommands.remove(i);
                break;
            }
        }
    }

    public void updateCommandByID(String id, OfflineNewTextbookCommand c){
        removeCommandByID(id);
        addOfflineBookCommand(c);
    }

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
