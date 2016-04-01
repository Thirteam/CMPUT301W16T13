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
