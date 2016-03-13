package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Fragment_Search extends BaseFragment {

    private  ListView lv_search_result;
    private  TextView tv_hint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_search, null);
        lv_search_result = (ListView) v.findViewById(R.id.lv_search_result);
        tv_hint = (TextView) v.findViewById(R.id.tv_search_hint);
        lv_search_result.setVisibility(View.GONE);
        tv_hint.setText(getResources().getString(R.string.new_search));
        tv_hint.setVisibility(View.VISIBLE);

        return v;
    }

    @Override
    public String getFragmentLabel(){
        return "Search";
    }


}
