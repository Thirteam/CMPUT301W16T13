package cmput301.textbookhub.Models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * <code>OfflineHelper</code> is used to save offline changes to local memory
 * <p>The files will then be pushed to the ElasticSearch server upon reconnecting to the internet</p>
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/30
 * @see OfflineCommandList
 * @see OfflineNewTextbookCommand
 *
 * Created by Fred on 2016/3/29.
 */
public class OfflineHelper {

    private static final String OFFLINE_USER_PROFILE = "user.sav";
    private static final String OFFLINE_COMMAND_LIST = "commands.sav";

    /**
     * <code>loadUserFromFile</code> gets the <code>User</code> from local memory.
     *
     * @param ctx the Context
     * @return the <code>User</code>, if none found returns <code>null</code>
     * */
    public User loadUserFromFile(Context ctx){
        try {
            FileInputStream fis = ctx.openFileInput(OFFLINE_USER_PROFILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<User>(){}.getType();
            return (User) gson.fromJson(in, listType);

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <code>saveUserFromFile</code> saves the <code>User</code> to local memory.
     *
     * @param ctx the Context
     * @param user the <code>User</code> to save
     * */
    public void saveUserToFile(Context ctx, User user){
        try {
            FileOutputStream fos = ctx.openFileOutput(OFFLINE_USER_PROFILE,
                    0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public OfflineCommandList loadCommandsFromFile(Context ctx){
        try {
            FileInputStream fis = ctx.openFileInput(OFFLINE_COMMAND_LIST);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<OfflineCommandList>(){}.getType();
            return (OfflineCommandList) gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new OfflineCommandList();
    }

    public void saveCommandsToFile(Context ctx, OfflineCommandList list){
        try {
            FileOutputStream fos = ctx.openFileOutput(OFFLINE_COMMAND_LIST,
                    0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(list, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
