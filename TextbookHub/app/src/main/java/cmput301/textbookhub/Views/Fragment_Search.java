package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import cmput301.textbookhub.Controllers.BaseController;
import cmput301.textbookhub.Controllers.MainActivityController;
import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Fragment_Search extends BaseFragment {

    private  ListView lv_search_result;
    private  TextView tv_hint;
    private  SearchListAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_search, null);
        lv_search_result = (ListView) v.findViewById(R.id.lv_search_result);
        tv_hint = (TextView) v.findViewById(R.id.tv_search_hint);
        searchView = (SearchView) v.findViewById(R.id.searchView);
        tv_hint.setText(getResources().getString(R.string.new_search));
        final MainActivityController activityController =  ((Activity_Main) getActivity()).getController();

        this.adapter = new SearchListAdapter(v.getContext(), R.layout.adapter_search_list, activityController.getCurrSearchResult());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                activityController.initNewSearch(query);
                adapter.notifyDataSetChanged();
                toggleView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });
        lv_search_result.setAdapter(adapter);

        toggleView();
        return v;
    }

    private void toggleView(){
        if(this.adapter.getCount() == 0){
            lv_search_result.setVisibility(View.GONE);
            tv_hint.setVisibility(View.VISIBLE);
        }else{
            lv_search_result.setVisibility(View.VISIBLE);
            tv_hint.setVisibility(View.GONE);
        }
    }

    @Override
    public String getFragmentLabel(){
        return "Search";
    }


}
