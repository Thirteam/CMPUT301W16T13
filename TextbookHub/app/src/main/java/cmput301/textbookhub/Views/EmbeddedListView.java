package cmput301.textbookhub.Views;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/4/2.
 */
public class EmbeddedListView extends ListView{

    private final int maxHeight;

    public EmbeddedListView(Context context) {
        this(context, null);
    }

    public EmbeddedListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmbeddedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ListViewMaxHeight);
            maxHeight = a.getDimensionPixelSize(R.styleable.ListViewMaxHeight_maxHeight, Integer.MAX_VALUE);
            a.recycle();
        } else {
            maxHeight = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
