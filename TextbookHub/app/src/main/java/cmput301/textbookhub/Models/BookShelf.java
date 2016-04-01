package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * Created by Fred on 2016/3/8.
 */
public class BookShelf {

    private ArrayList<Textbook> allBooks;


    public BookShelf(){
        this.allBooks = new ArrayList<>();

    }

    public BookShelf(ArrayList<Textbook> allBooks){
        this.allBooks = allBooks;
    }

    public void addNewTextBook(Textbook book){
        this.allBooks.add(book);
    }

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

    public ArrayList<Textbook> getAvailableBooks() {
        ArrayList rv = new ArrayList<>();
        for(Textbook t : allBooks){
            if(!t.getBookStatus().equals(BookStatus.BORROWED)){
                rv.add(t);
            }
        }
        return rv;
    }

    public void populateBookShelf(ArrayList<Textbook> allBooks){
        this.allBooks.clear();
        this.allBooks.addAll(allBooks);
    }
}
