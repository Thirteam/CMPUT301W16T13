package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/8.
 *
 * Base controller class responsible for handling activity related tasks
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



}
