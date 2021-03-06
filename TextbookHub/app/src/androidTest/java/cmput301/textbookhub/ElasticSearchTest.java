package cmput301.textbookhub;

import android.os.AsyncTask;

import java.util.ArrayList;

import cmput301.textbookhub.Models.DataHelper;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.User;

/**
 * Created by Jayden on 2016-03-12.
 *
 * Unfortunately asynchronous operations are ultimately unable to use the test case format
 * as test cases will not produce more threads. As such these can be placed into any activity
 * in a button or other such event to run the test. You can verify the test using the REST
 * client or by looking at your inventory (i suppose.)
 *
 * Make sure to create an instance of the class before running any of the methods.
 * This is to allow you to reference the method from a non-static context
 *
 * Example:
 * ElasticSearchTest est = new ElasticSearchTest();
 * est.testAddTextbookTask();
 *
 * That should add 6 textbooks into the test user "Jayden" automatically.
 */
public class ElasticSearchTest{

    public void testAddTextbookTask(){

        User testUser = new User("Jayden", "12345");
        Textbook testBook0 = new Textbook(testUser,"The Great Textbook");
        Textbook testBook1 = new Textbook(testUser,"The Great Textbook 2");
        Textbook testBook2 = new Textbook(testUser,"The Great Textbook 3");
        Textbook testBook3 = new Textbook(testUser,"The Great Textbook 4");
        Textbook testBook4 = new Textbook(testUser,"The Great Textbook 5");
        Textbook testBook5 = new Textbook(testUser,"The Great Textbook 6");

        AsyncTask<Textbook, Void, ArrayList<Textbook>> execute = new DataHelper.AddTextbookTask();
        execute.execute(testBook0, testBook1, testBook2, testBook3, testBook4, testBook5);

    }

}
