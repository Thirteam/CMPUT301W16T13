package cmput301.textbookhub.Models;

import java.util.ArrayList;

/**
 * Created by Fred on 2016/3/8.
 */
public class BookShelf {

    private ArrayList<TextBook> personalBooks;
    private ArrayList<TextBook> borrowedBooks;

    public BookShelf(){
        this.personalBooks = new ArrayList<>();
        this.borrowedBooks = new ArrayList<>();
    }

    public BookShelf(ArrayList personalBooks, ArrayList borrowedBooks){
        this.personalBooks = personalBooks;
        this.borrowedBooks = borrowedBooks;
    }

    public void addPersonalBook(TextBook book){
        this.personalBooks.add(book);
    }

    public void addBorrowedBook(TextBook book){
        this.borrowedBooks.add(book);
    }

    public ArrayList<TextBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public ArrayList<TextBook> getPersonalBooks() {
        return personalBooks;
    }

    public int getPersonalBooksAmount(){
        return this.personalBooks.size();
    }

    public int getBorrowedBooksAmount(){
        return this.borrowedBooks.size();
    }


}
