package cmput301.textbookhub.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/4/3.
 */
public class PicturePagerAdapter extends PagerAdapter{

    Context context;
    LayoutInflater inflater;
    ArrayList<Bitmap> data;
    ViewPager pager;
    private boolean deleteEnable;

    public PicturePagerAdapter(Context ctx, ArrayList<Bitmap> data, ViewPager p, boolean delete_enable){
        this.context = ctx;
        this.data = data;
        this.pager = p;
        this.deleteEnable = delete_enable;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView picture;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.pager_adapter_pictures, null);
        picture = (ImageView)v.findViewById(R.id.iv_picture);
        BitmapDrawable drawable = new BitmapDrawable(data.get(position));
        picture.setImageDrawable(drawable);
        if(deleteEnable) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle(context.getResources().getString(R.string.actions_title));
                    b.setMessage(context.getResources().getString(R.string.actions_picture));
                    b.setNegativeButton(context.getResources().getString(R.string.delete_picture), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            removeBitmapAt(pager, position);
                        }
                    });
                    b.show();
                }
            });
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((LinearLayout) object);
    }

    public void addBitmap(Bitmap map){
        pager.setAdapter (null);
        this.data.add(map);
        pager.setAdapter(this);
    }

    public void removeBitmapAt(ViewPager pager, int index){
        pager.setAdapter (null);
        this.data.remove(index);
        pager.setAdapter (this);
    }

}
