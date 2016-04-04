package cmput301.textbookhub.Models;

/**
 * <code>OfflineNewTextbookCommand</code> is used to send commands from OfflineHelper to DataHelper
 * <p>The files will then be pushed to the ElasticSearch server upon reconnecting to the internet</p>
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/30
 * @see OfflineCommandList
 * @see OfflineHelper
 * @see DataHelper
 *
 * Created by Fred on 2016/3/30.
 */
public class OfflineNewTextbookCommand {

    Textbook book;

    public OfflineNewTextbookCommand(Textbook t){
        this.book = t;
    }

    /**
     * <code>execute</code> executes the <code>AddTextbookTask</code>.
     */
    public void execute() {
        if(this.book != null) {
            DataHelper.AddTextbookTask e = new DataHelper.AddTextbookTask();
            e.execute(book);
            this.book = null;
        }
    }

    /**
     * <code>getRelatedBook</code> returns the <code>Textbook</code> associated with this command
     *
     * @return <code>Textbook</code>
     */
    public Textbook getRelatedBook(){
        return this.book;
    }

    /**
     * <code>getCommandID</code> retrieves the ID of the <code>Textbook</code>
     *
     * @return ID of the <code>Textbook</code>
     */
    public String getCommandID() {
        return this.book.getID();
    }
}
