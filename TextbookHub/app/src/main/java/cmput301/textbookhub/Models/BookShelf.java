package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class BookShelf {

    private ArrayList<TextBook> allBooks;


    public BookShelf(){
        this.allBooks = new ArrayList<>();

    }

    public BookShelf(ArrayList<TextBook> allBooks){
        this.allBooks = allBooks;
    }

    public void addNewTextBook(TextBook book){
        this.allBooks.add(book);
    }

    public ArrayList<TextBook> getAllBooks() {
        return allBooks;
    }

    public ArrayList<TextBook> getBorrowedBooks() {
        ArrayList rv = new ArrayList<>();
        for(TextBook t : allBooks){
            if(t.getBookStatus().equals(BookStatus.BORROWED)){
                rv.add(t);
            }
        }
        return rv;
    }

    public ArrayList<TextBook> getAvailableBooks() {
        ArrayList rv = new ArrayList<>();
        for(TextBook t : allBooks){
            if(!t.getBookStatus().equals(BookStatus.BORROWED)){
                rv.add(t);
            }
        }
        return rv;
    }

    public void populateBookShelf(ArrayList<TextBook> allBooks){
        this.allBooks = allBooks;
    }
}
