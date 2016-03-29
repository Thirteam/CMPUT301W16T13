package cmput301.textbookhub.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmput301.textbookhub.Tools;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/**
 * Created by Jayden on 2016-03-12.
 *
 * Based on the code built by esports
 */
public class DataHelper {
    private static JestDroidClient    client;

    public static class GetTextbookTask extends AsyncTask<String, Void, ArrayList<TextBook>>{
        @Override
        protected ArrayList<TextBook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<TextBook> textbooks =  new ArrayList<TextBook>();

            if (Tools.isStringValid(search_strings[0])){
                search_string = "{\"query\": {\"match\":{ \"id\": \"" + search_strings[0] + "\"}}}";
            }

            //Note: I'm making a huge assumption here, that only the first search term will be used.
            Search search = new Search.Builder(search_string)
                    .addIndex("thirteam")
                    .addType("textbook")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //Search our list of tweets
                    List<TextBook> returned_books = execute.getSourceAsObjectList(TextBook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("TODO", "we actually failed here");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    public static class UpdateTextbookTask extends AsyncTask<TextBook, Void, Void> {
        @Override
        protected Void doInBackground(TextBook... books){
            Update update = new Update.Builder(books[0]).index("thirteam").type("textbook").build();
            try {
                DocumentResult result = client.execute(update);
                if (result.isSucceeded()) {

                }else{
                    //TODO add an error message
                    Log.i("TODO", "we actually failed here");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users){
            String script_pass = "{\"doc\":{" +
                    "\"password\" : \""+users[0].getPassword()+"\"" +
                    "}}";
            String script_email = "{\"doc\":{" +
                    "\"email\" : \""+users[0].getEmail()+"\"" +
                    "}}";
            Update update_pass = new Update.Builder(script_pass).index("thirteam").type("user").id(users[0].getJid()).build();
            Update update_email = new Update.Builder(script_email).index("thirteam").type("user").id(users[0].getJid()).build();
            try {
                DocumentResult result_pass = client.execute(update_pass);
                DocumentResult result_email = client.execute(update_email);
                if (!result_pass.isSucceeded()) {
                    Log.i("ERROR", result_pass.getErrorMessage() + script_pass);
                }
                if(!result_email.isSucceeded()){
                    Log.i("ERROR", result_email.getErrorMessage() + script_email);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class SearchTextbookTask extends AsyncTask<String, Void, ArrayList<TextBook>>{
        @Override
        protected ArrayList<TextBook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<TextBook> textbooks =  new ArrayList<TextBook>();

            for(String key:search_strings) {
                if (Tools.isStringValid(search_strings[0])) {
                    search_string = "{\"query\": {\"match\":{ \"bookName\": \"" + key + "\"}}}";
                }

                //Note: I'm making a huge assumption here, that only the first search term will be used.
                Search search = new Search.Builder(search_string)
                        .addIndex("thirteam")
                        .addType("textbook")
                        .build();

                try {
                    SearchResult execute = client.execute(search);
                    if (execute.isSucceeded()) {
                        //Search our list of tweets
                        List<TextBook> returned_books = execute.getSourceAsObjectList(TextBook.class);
                        textbooks.addAll(returned_books);
                    } else {
                        //TODO add an error message
                        Log.i("TODO", "we actually failed here");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return textbooks;
        }
    }

    public static class GetAllTextbookTask extends AsyncTask<String, Void, ArrayList<TextBook>>{
        @Override
        protected ArrayList<TextBook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<TextBook> textbooks =  new ArrayList<TextBook>();

            if (Tools.isStringValid(search_strings[0])){
                search_string = "{\"query\": {\"match\":{ \"owner.username\": \"" + search_strings[0] + "\"}}}";
            }

            //Note: I'm making a huge assumption here, that only the first search term will be used.
            Search search = new Search.Builder(search_string)
                    .addIndex("thirteam")
                    .addType("textbook")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //Search our list of tweets
                    List<TextBook> returned_books = execute.getSourceAsObjectList(TextBook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("TODO", "we actually failed here");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    public static class DeleteTextbookTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ids){
            verifyClient();
            Delete del = new Delete.Builder(ids[0]).index("thirteam").type("textbook").build();
            try {
                DocumentResult result = client.execute(del);
                if (result.isSucceeded()) {

                }else{
                    //TODO add an error message
                    Log.i("TODO", "we actually failed here");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class AddTextbookTask extends AsyncTask<TextBook, Void, ArrayList<TextBook>> {

        private ArrayList<TextBook> books = new ArrayList<>();

        @Override
        protected ArrayList<TextBook> doInBackground(TextBook... textbooks){
            verifyClient();

            for (int i = 0; i<textbooks.length; i++) {
                TextBook book = textbooks[i];

                Index index = new Index.Builder(book).index("thirteam").type("textbook").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result != null && result.isSucceeded()) {
                        //Set the ID to tweet that elasticsearch told me it was
                        book.setJid(result.getId());
                        books.add(book);
                    }else{
                        //TODO add an error message
                        Log.i("TODO", "we actually failed here");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this.books;
        }


    }

    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<User>>{
        @Override
        protected ArrayList<User> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<User> users =  new ArrayList<User>();

            if (Tools.isStringValid(search_strings[0])){
                search_string = "{\"query\": {\"match\":{ \"username\": \"" + search_strings[0] + "\"}}}";
            }

            //Note: I'm making a huge assumption here, that only the first search term will be used.
            Search search = new Search.Builder(search_string)
                    .addIndex("thirteam")
                    .addType("user")
                    .build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //Search our list of tweets
                    List<User> userList = execute.getSourceAsObjectList(User.class);
                    users.addAll(userList);
                }else{
                    //TODO add an error message
                    Log.i("TODO", "we actually failed here");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }
    }

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users){
            verifyClient();

            for (int i = 0; i<users.length; i++) {
                User user = users[i];

                Index index = new Index.Builder(user).index("thirteam").type("user").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        //Set the ID to tweet that elasticsearch told me it was
                        user.setJid(result.getId());
                    }else{
                        //TODO add an error message
                        Log.i("TODO", "we actually failed here");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static void verifyClient(){
        //1. Verify the client exists
        //2. If is doesn't, make it
        if (client == null){
            //2. If it doesn't make it
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
