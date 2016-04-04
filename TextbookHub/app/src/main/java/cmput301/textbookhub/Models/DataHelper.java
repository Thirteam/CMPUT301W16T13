package cmput301.textbookhub.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
 * <code>DataHelper</code> is used to upload and download information to/from Elastic Search.
 *
 * @author Thirteam
 * @version 1.4
 * @since 2016/03/12
 *
 * Created by Jayden on 2016-03-12.
 *
 * Based on the code built by esports
 */
public class DataHelper {
    private static JestDroidClient    client;

    /**
     * Get <code>Textbook</code>(s) from the ElasticSearch server.
     * <p>This class runs a background task to obtain a list of <code>Textbook</code>s <br>
     *     In order to populate the <code>BookShelf</code></p>
     * */
    public static class GetTextbookTask extends AsyncTask<String, Void, ArrayList<Textbook>>{

        /**
         * Get a <code>Textbook</code>(s) from ElasticSearch
         *
         * @param search_strings the parameters to search ElasticSearch with (username)
         * @return An ArrayList of the <code>Textbook</code>(s) returned by the query.
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<Textbook> textbooks =  new ArrayList<Textbook>();

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
                    List<Textbook> returned_books = execute.getSourceAsObjectList(Textbook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("ERROR GETTING TEXTBOOK", execute.getErrorMessage());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    /**
     * Update the information of the current <code>User</code> on the ElasticSearch server.
     * <p>This class runs a background task to update <code>User</code> without reducing <br>
     *     the fluidity of the app..</p>
     * */
    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {

        /**
         * Update the current <code>User</code> information on ElasticSearch.
         *
         * @param users the <Code>User</Code>(s) to update from local memory.
         * @return <code>null</code>
         * */
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

    /**
     * This class is used mainly to test server connectivity
     * <p>This class runs this background task so that it may test for online connectivity <br>
     *     With the intention of pushing all changes on local memory to ElasticSearch should <br>
     *     Online connectivity resume/begin.</p>
     * */
    public static class TestServerConnectionTask  extends AsyncTask<Void, Void, Boolean>{

        /**
         * Test the current connection to the ElasticSearch server.
         *
         * @param params <code>null</code> parameter(s) to test connection.
         * @return <code>true</code> for connecting or <code>false</code> for timing out.
         * */
        @Override
        protected Boolean doInBackground(Void... params) {
            verifyClient();
            Search search = new Search.Builder("{\"query\":{\"match_all\": {} }}").addIndex("thirteam").addType("textbook").build();
            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    //Search our list of tweets
                    return true;
                } else {
                    //TODO add an error message
                    Log.i("SERVER CONNECTION ERR", execute.getErrorMessage());
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Search for <code>Textbook</code>(s) from the ElasticSearch server.
     * <p>This class runs a background task to obtain a list of <code>Textbook</code>s <br>
     *     that match the search_strings</p>
     * */
    public static class SearchTextbookTask extends AsyncTask<String, Void, ArrayList<Textbook>>{

        /**
         * Get a list of <code>Textbook</code>(s) that match the criteria set by <code>search_strings</code>.
         *
         * @param search_strings the keywords used in the query
         * @return An ArrayList of <code>Textbook</code>(s) matching the search_strings
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<Textbook> textbooks =  new ArrayList<Textbook>();

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
                        List<Textbook> returned_books = execute.getSourceAsObjectList(Textbook.class);
                        textbooks.addAll(returned_books);
                    } else {
                        //TODO add an error message
                        Log.i("ERROR SEARCH", execute.getErrorMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return textbooks;
        }
    }

    /**
     * Get all <code>Textbook</code>(s) from the ElasticSearch server.
     * <p>This class runs a background task to obtain a list of all <code>Textbook</code>s</p>
     * */
    public static class GetAllTextbookTask extends AsyncTask<String, Void, ArrayList<Textbook>>{

        /**
         * Gets all the <code>Textbook</code>s from the ElasticSearch server
         *
         * @param search_strings
         * @return An ArrayList of all <code>Textbook</code>(s) from the ElasticSearch server
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<Textbook> textbooks =  new ArrayList<>();

            if (Tools.isStringValid(search_strings[0])){
                search_string = "{\"query\": {\"match\":{ \"owner\": \"" + search_strings[0] + "\"}}}";
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
                    List<Textbook> returned_books = execute.getSourceAsObjectList(Textbook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("ERROR GETTING ALL", execute.getErrorMessage());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    /**
     * Get bidded <code>Textbook</code>(s) by a specific bidder from the ElasticSearch server.
     * <p>This class runs a background task to obtain a list of <code>Textbook</code>s <br>
     *     In order to populate the My Bids activity with <code>Textbook</code>(s) the <br>
     *     current user has bidded on.</p>
     * */
    public static class GetBiddedTextbookByBidderTask extends AsyncTask<String, Void, ArrayList<Textbook>>{

        /**
         * Gets the bidded <code>Textbook</code>(s) by the <code>User</code>.
         *
         * @param search_strings the <code>User</code> to query for
         * @return An ArrayList of <code>Textbook</code>(s) matching the query.
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<Textbook> textbooks =  new ArrayList<Textbook>();
            search_string = "{\"query\":{\"filtered\":{\"query\": {\"term\":{ \"bids.bidList.bidder\":\"" + search_strings[0] +"\"}}, \"filter\":{\"not\":{\"term\":{\"owner\":\""+search_strings[0]+"\"}}}}}}";

            //Note: I'm making a huge assumption here, that only the first search term will be used.
            Search search = new Search.Builder(search_string)
                    .addIndex("thirteam")
                    .addType("textbook")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //Search our list of tweets
                    List<Textbook> returned_books = execute.getSourceAsObjectList(Textbook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("ERROR GETTING BY BIDDER", execute.getErrorMessage());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    /**
     * Get <code>Textbook</code>(s) currently borrowed from by a specific <code>User</code> from the ElasticSearch server.
     * <p>This class runs a background task to obtain a list of <code>Textbook</code>s <br>
     *     In order to populate the Borrowed activity</p>
     * */
    public static class GetTextbookByBorrowerTask extends AsyncTask<String, Void, ArrayList<Textbook>>{

        /**
         * Gets the <code>Textbook</code>(s) borrowed by the current <code>User</code>.
         *
         * @param search_strings the <code>User</code> to get a list of borrowed <code>Textbook</code>(s) from.
         * @return An ArrayList of borrowed <code>Textbook</code>(s) for the current <code>User</code>.
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(String... search_strings){
            verifyClient();

            String search_string = "";

            //Start our initial array list (empty)
            ArrayList<Textbook> textbooks =  new ArrayList<>();

            if (Tools.isStringValid(search_strings[0])){
                search_string = "{\"query\": {\"term\":{ \"borrower\": \"" + search_strings[0] + "\"}}}";
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
                    List<Textbook> returned_books = execute.getSourceAsObjectList(Textbook.class);
                    textbooks.addAll(returned_books);
                }else{
                    //TODO add an error message
                    Log.i("ERR GETTING BY BORROWER", execute.getErrorMessage());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return textbooks;
        }
    }

    /**
     * Delete <code>Textbook</code>(s) from the ElasticSearch server.
     * <p>This class runs a background task to delete a list of <code>Textbook</code>(s) <br>
     *     from the ElasticSearch server</p>
     * */
    public static class DeleteTextbookTask extends AsyncTask<String, Void, Void> {

        /**
         * Removes the <code>Textbook</code>(s) with the id(s) matching those in ids.
         *
         * @param ids the id(s) of the <code>Textbook</code>(s) to be removed
         * @return <code>null</code>
         * */
        @Override
        protected Void doInBackground(String... ids){
            verifyClient();
            Delete del = new Delete.Builder(ids[0]).index("thirteam").type("textbook").build();
            try {
                DocumentResult result = client.execute(del);
                if (!result.isSucceeded()) {
                    Log.i("ERROR DELETING TEXTBOOK", result.getErrorMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Add a <code>Textbook</code>(s) to the ElasticSearch server.
     * <p>This class runs a background task to add a <code>Textbook</code> to the ElasticSearch server.</p>
     * */
    public static class AddTextbookTask extends AsyncTask<Textbook, Void, ArrayList<Textbook>> {
        
        private ArrayList<Textbook> books = new ArrayList<>();

        /**
         * Adds the <code>Textbook</code> to the ElasticSearch server
         *
         * @param textbooks the <code>Textbook</code>(s) to be added to the server.
         * @return An ArrayList of the <code>Textbook</code>(s) added.
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(Textbook... textbooks){
            verifyClient();

            for (int i = 0; i<textbooks.length; i++) {
                Textbook book = textbooks[i];

                Index index = new Index.Builder(book).index("thirteam").type("textbook").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result != null && result.isSucceeded()) {
                        //Set the ID to tweet that elasticsearch told me it was
                        book.setJid(result.getId());
                        books.add(book);
                    }else{
                        //TODO add an error message
                        if (!result.isSucceeded()) {
                            Log.i("ERROR ADDING TEXTBOOK", result.getErrorMessage());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this.books;
        }
    }



    /**
     * Update the <code>Textbook</code>(s) information on ElasticSearch from local memory.
     * <p>This class runs a background task to obtain a list of <code>Textbook</code>s <br>
     *     In order to populate the <code>BookShelf</code></p>
     * */
    public static class UpdateTextbookTask extends AsyncTask<Textbook, Void, ArrayList<Textbook>> {

        private ArrayList<Textbook> books = new ArrayList();

        /**
         * Updates the <code>Textbook</code>(s) information on the server.
         *
         * @param textbooks
         * @return <code>true</code> for connecting or <code>false</code> for timing out.
         * */
        @Override
        protected ArrayList<Textbook> doInBackground(Textbook... textbooks){
            verifyClient();

            for (int i = 0; i<textbooks.length; i++) {
                Textbook book = textbooks[i];
                //The only real difference from Add and Edit is this line including the existing ID
                Delete del = new Delete.Builder(book.getJid()).index("thirteam").type("textbook").build();
                book.setJid(null);
                Index index = new Index.Builder(book).index("thirteam").type("textbook").build();
                try {
                    client.execute(del);
                    DocumentResult result = client.execute(index);
                    if (result != null && result.isSucceeded()) {
                        books.add(book);
                    }else{
                        //TODO add an error message
                        Log.i("ERROR EDITING TEXTBOOK", result.getErrorMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this.books;
        }
    }

    /**
     * Get <code>User</code>(s) from the ElasticSearch server.
     * <p>This class runs a background task to obtain an ArrayList of <code>Users</code>(s) </p>
     * */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<User>>{

        /**
         * Gets <code>User</code> from the server
         *
         * @param search_strings <code>User</code> to get from server.
         * @return An ArrayList of <code>User</code>(s)
         * */
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
                    Log.i("ERROR GETTING USER", execute.getErrorMessage());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

            return users;
        }
    }

    /**
     * Adds <code>User</code>(s) to the ElasticSearch server
     * <p>This class runs a background task to obtain a list of <code>User</code>(s).</p>
     * */
    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        /**
         * Adds the <code>User</code>(s) to the Server
         *
         * @param users the list of <code>User</code>(s) to add.
         * @return <code>null</code>
         * */
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
                        Log.i("ERROR ADDING USER", result.getErrorMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class GetLocationFromAddressTask  extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params) {
            return getLocationInfo(params[0]);
        }
    }
    private static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            address = address.replaceAll(" ","%20");

            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * Verify the connection to the ElasticSearch server.
     *
     * */
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
