package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/10.
 */
public class BidHistListAdapter extends ArrayAdapter{

    private Context ctx;
    private int layoutResID;
    private ArrayList<Bid> data;

    public BidHistListAdapter(Context context, int layoutResourceId, ArrayList<Bid> data){
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
    public Bid getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            convertView = inflater.inflate(layoutResID, parent, false);
            ((TextView) convertView.findViewById(R.id.tvl_bid_amount)).setText(getItem(position).getAmount().toString());
        }
        return convertView;
    }
}
