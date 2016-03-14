package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * Created by Fred on 2016/3/8.
 */
public class BookShelf {

    private ArrayList<TextBook> allBooks;


    public BookShelf(){
        this.allBooks = new ArrayList<>();

    }

    public BookShelf(ArrayList<TextBook> allBooks){
        this.allBooks = allBooks;
    }

    public void addNewBook(TextBook book){
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
