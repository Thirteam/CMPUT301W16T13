package cmput301.textbookhub.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.BaseView;

/**
 * <code>ActivityController</code> class responsible for handling activity related tasks
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/08
 *
 * Created by Fred on 2016/3/8.
 *
 */
public abstract class ActivityController extends BaseController{

    private ArrayList<BaseView> views;

    public ActivityController(){
        //http://cmput301.softwareprocess.es:8080/thirteam/
        this.views = new ArrayList<>();
    }



    public BookStatus findBookStatus(String name) throws IllegalArgumentException{
        for(BookStatus status : BookStatus.values()){
            if(status.equalsName(name)){
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    public ArrayList<BaseView> getBaseViews() {
        return views;
    }

    public void setBaseViews(ArrayList<BaseView> list) {
        this.views = list;
    }

    public void addBaseView(BaseView view) {
        this.views.add(view);
    }

    public void updateBaseViews() {
        for(BaseView v : this.views)
            v.updateView();
    }

    public void displayNotificationDialog(Context ctx, String title, String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton(ctx.getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void clearOnScreenKsyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isOkToQuery(String id){
        try{
            Textbook b = queryTextbook(id);
            return true;
        }catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
    }

}
