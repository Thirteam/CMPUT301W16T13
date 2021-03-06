package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import cmput301.textbookhub.Models.Bid;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/3/10.
 */
public class BidHistListAdapter extends ArrayAdapter{

    private Context ctx;
    private int layoutResID;
    private ArrayList<Bid> data;
    private Double currMax = 0.0;

    public BidHistListAdapter(Context context, int layoutResourceId, ArrayList<Bid> data){
        super(context, layoutResourceId, data);
        this.data = data;
        this.ctx = context;
        this.layoutResID = layoutResourceId;
        if(data.size()!=0){
            this.currMax = data.get(0).getAmount();
        }
    }

    @Override
    public void add(Object object) {
        super.add(object);
        if(((Bid)object).getAmount()>=this.currMax){
            this.currMax = ((Bid)object).getAmount();
        }
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
        }
        ((TextView) convertView.findViewById(R.id.tvl_bid_amount)).setText(getItem(position).getAmount().toString());
        if(getItem(position).getAmount()>=this.currMax){
            ((TextView) convertView.findViewById(R.id.tvl_bid_amount)).setTextColor(convertView.getResources().getColor(R.color.red));
            this.currMax = getItem(position).getAmount();
        }else{
            ((TextView) convertView.findViewById(R.id.tvl_bid_amount)).setTextColor(convertView.getResources().getColor(R.color.mat_grey_superdark));
        }
        return convertView;
    }
}
