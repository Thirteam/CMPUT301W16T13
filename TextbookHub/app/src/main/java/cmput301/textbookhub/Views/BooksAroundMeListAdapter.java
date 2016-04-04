package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/3/10.
 */
public class BooksAroundMeListAdapter extends ArrayAdapter{

    private Context ctx;
    private int layoutResID;
    private ArrayList<Textbook> data;
    private LatLng currLocation;

    public BooksAroundMeListAdapter(Context context, int layoutResourceId, ArrayList<Textbook> data, LatLng currLocation){
        super(context, layoutResourceId, data);
        this.data = data;
        this.ctx = context;
        this.layoutResID = layoutResourceId;
        this.currLocation = currLocation;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Textbook getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            convertView = inflater.inflate(layoutResID, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.tvl_book_name)).setText(getItem(position).getName());
        String distance = Tools.roundDecimal(2,
                Tools.calculateDistanceInMeters(
                        getItem(position).getLat(),
                        getItem(position).getLon(),
                        currLocation.getLatitude(),
                        currLocation.getLongitude()
                )/1000.0
        )+" km";
        ((TextView) convertView.findViewById(R.id.tvl_distance)).setText(distance);
        return convertView;
    }
}
