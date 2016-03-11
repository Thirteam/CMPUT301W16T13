package cmput301.textbookhub.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cmput301.textbookhub.Models.BookStatus;
import cmput301.textbookhub.Models.DataBundleLabel;
import cmput301.textbookhub.Views.BaseView;

/**
 * Created by Fred on 2016/3/8.
 */
public abstract class BaseController {

    private Context ctx;

    protected BaseController(Context ctx){
        //TODO:init database here
        this.ctx = ctx;
    }

    public Context getContext() {
        return this.ctx;
    }

    public boolean isNetworkAvailable() {
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

    public abstract void addBaseViews(BaseView view);

    public abstract void updateBaseViews();

}
