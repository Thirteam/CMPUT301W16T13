package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * <code>BookShelf</code> used to keep a list of <code>Textbook</code>s and aide in the manipulation of textbooks.
 *
 * @author Thirteam
 * @version 1.1
 * @since 2016/03/08
 * @see User
 * @see Textbook
 * @see BidList
 *
 * Created by Fred on 2016/3/8.
 */
public class BookShelf {

    private ArrayList<Textbook> allBooks;

    public BookShelf(){
        this.allBooks = new ArrayList<>();
    }


    /**
     * Remove a <code>Textbook</code> from the bookshelf.
     *
     * @param book the Textbook to be removed
     * */
    public void removeBook(Textbook book){
        if(this.allBooks.contains(book)){
            this.allBooks.remove(book);
        }
        int i = 0;
        for(Textbook b: this.allBooks){
            if(b.getID().equals(book.getID())){
                this.allBooks.remove(i);
                break;
            }
            i++;
        }
    }

    /**
     * Adds a <code>Textbook</code> to the <code>BookShelf</code>.
     *
     * @param book the Textbook to be added
     * */
    public void addNewTextBook(Textbook book){
        this.allBooks.add(book);
    }

    /**
     * Gets all <code>Textbook</code>(s) from the <code>BookShelf</code>.
     *
     * @return an ArrayList of the <code>Textbook</code>(s) in the <code>Bookshelf</code>.
     * */
    public ArrayList<Textbook> getAllBooks() {
        return allBooks;
    }

    public ArrayList<Textbook> getBorrowedBooks() {
        ArrayList rv = new ArrayList<>();
        for(Textbook t : allBooks){
            if(t.getBookStatus().equals(BookStatus.BORROWED)){
                rv.add(t);
            }
        }
        return rv;
    }

    /**
     * Gets all available <code>Textbook</code>(s) from the <code>BookShelf</code>.
     *
     * @see Textbook
     * @return an ArrayList of available <code>Textbook</code>(s) in the <code>Bookshelf</code>.
     * */
    public ArrayList<Textbook> getAvailableBooks() {
        ArrayList rv = new ArrayList<>();
        for(Textbook t : allBooks){
            if(!t.getBookStatus().equals(BookStatus.BORROWED)){
                rv.add(t);
            }
        }
        return rv;
    }

    /**
     * Adds all <code>Textbook</code>(s) from the ArrayList
     *
     * @param allBooks the ArrayList of <code>Textbook</code>(s).
     * */
    public void populateBookShelf(ArrayList<Textbook> allBooks){
        this.allBooks.clear();
        this.allBooks.addAll(allBooks);
    }

    /**
     * Checks if a <code>Textbook</code> in the <code>BookShelf</code> has a new bid.
     *
     * @return true if there is a new bid, false if there is not.
     * */
    public boolean someBookHasNewBids(){
        for(Textbook b:this.allBooks){
            if(b.getViewStatus().equals(ViewStatus.HAS_NEW_BID))
                return true;
        }
        return false;
    }
}
