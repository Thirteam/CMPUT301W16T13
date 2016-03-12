package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.DataBundleLabel;
import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/8.
 */
public abstract class BaseController {

    private ArrayList<BaseView> views;

    public BaseController(){
        //TODO:init database here
        //http://cmput301.softwareprocess.es:8080/thirteam/
        this.views = new ArrayList<>();
    }

    public boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public BookStatus findBookStatus(String name) throws IllegalArgumentException{
        for(BookStatus status : BookStatus.values()){
            if(status.equalsName(name)){
                return status;
            }
        }
        throw new IllegalArgumentException();
    }

    public DataBundleLabel findDataBundleLabel(String name) throws IllegalArgumentException{
        for(DataBundleLabel label : DataBundleLabel.values()){
            if(label.equalsName(name)){
                return label;
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

}
