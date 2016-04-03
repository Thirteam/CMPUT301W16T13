package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301.textbookhub.Models.Textbook;
import cmput301.textbookhub.Models.ViewStatus;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/10.
 */
public class InventoryListAdapter extends ArrayAdapter{

    private Context ctx;
    private int layoutResID;
    private ArrayList<Textbook> data;

    public InventoryListAdapter(Context context, int layoutResourceId, ArrayList<Textbook> data){
        super(context, layoutResourceId, data);
        this.data = data;
        this.ctx = context;
        this.layoutResID = layoutResourceId;

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
        LinearLayout layout_indicator = (LinearLayout) convertView.findViewById(R.id.layout_indicator);
        if(getItem(position).getViewStatus().equals(ViewStatus.HAS_NEW_BID)){
            layout_indicator.setVisibility(View.VISIBLE);
            Log.i("SETTING VIS", "VIS for " + getItem(position).getName());
        }else{
            layout_indicator.setVisibility(View.INVISIBLE);
            Log.i("SETTING VIS", "INVIS for " + getItem(position).getName());
        }
        ((TextView) convertView.findViewById(R.id.tvl_book_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.tvl_status)).setText(getItem(position).getBookStatus().toString());
        return convertView;
    }

    public void clearIndicator(String id){
        for(Textbook b : this.data){
            if(b.getID().equals(id))
                b.flagAllBidsViewed();
        }
        notifyDataSetChanged();
    }
}
