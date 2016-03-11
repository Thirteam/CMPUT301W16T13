package cmput301.textbookhub.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cmput301.textbookhub.Models.TextBook;

/**
 * Created by Fred on 2016/3/10.
 */
public class BookListAdapter extends ArrayAdapter{

    private Context ctx;
    private int layoutResID;
    private ArrayList<TextBook> data;

    public BookListAdapter(Context context, int layoutResourceId, ArrayList<TextBook> data){
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
    public TextBook getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            convertView = inflater.inflate(layoutResID, parent, false);
        }
        return convertView;
    }
}
