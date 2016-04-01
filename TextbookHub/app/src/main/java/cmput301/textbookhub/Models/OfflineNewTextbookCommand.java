package cmput301.textbookhub.Models;

/**
 * Created by Fred on 2016/3/30.
 */
public class OfflineNewTextbookCommand {

    Textbook book;

    public OfflineNewTextbookCommand(Textbook t){
        this.book = t;
    }

    public void execute() {
        if(this.book != null) {
            DataHelper.AddTextbookTask e = new DataHelper.AddTextbookTask();
            e.execute(book);
            this.book = null;
        }
    }

    public Textbook getRelatedBook(){
        return this.book;
    }

    public String getCommandID() {
        return this.book.getID();
    }
}
