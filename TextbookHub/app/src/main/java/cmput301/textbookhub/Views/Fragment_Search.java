package cmput301.textbookhub.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MainActivityController;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;
import cmput301.textbookhub.Tools;

/**
 * Created by Fred on 2016/2/29.
 */
public class Fragment_Search extends BaseFragment implements NetworkStateObserver{

    private  ListView lv_search_result;
    private  TextView tv_hint, tv_no_conn_hint;
    private  SearchListAdapter adapter;
    private EditText et_search;
    private Button btn_search;
    AppUserController userController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_search, null);
        lv_search_result = (ListView) v.findViewById(R.id.lv_search_result);
        tv_hint = (TextView) v.findViewById(R.id.tv_search_hint);
        et_search = (EditText) v.findViewById(R.id.et_search);
        btn_search = (Button) v.findViewById(R.id.btn_search);
        tv_no_conn_hint = (TextView) v.findViewById(R.id.tv_no_conn_hint);
        userController = AppUserController.getInstance();
        tv_hint.setText(getResources().getString(R.string.new_search));
        final MainActivityController activityController =  ((Activity_Main) getActivity()).getActivityController();
        this.btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userController.hasInternetAccess(getContext()) && userController.hasServerAccess()) {
                    if (Tools.isStringValid(et_search.getText().toString())) {
                        adapter.clear();
                        adapter.addAll(activityController.initNewSearch(et_search.getText().toString()));
                        adapter.notifyDataSetChanged();
                        lv_search_result.setAdapter(adapter);
                    }
                }
            }
        });
        this.adapter = new SearchListAdapter(v.getContext(), R.layout.adapter_search_list, activityController.getCurrSearchResult());
        lv_search_result.setAdapter(adapter);
        toggleView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    @Deprecated
    public void toggleView(){
        if(userController.hasInternetAccess(getContext())) {
            tv_no_conn_hint.setVisibility(View.INVISIBLE);
            if (this.adapter.getCount() == 0) {
                lv_search_result.setVisibility(View.INVISIBLE);
                tv_hint.setVisibility(View.VISIBLE);
            } else {
                lv_search_result.setVisibility(View.VISIBLE);
                tv_hint.setVisibility(View.INVISIBLE);
            }
        }else{
            tv_hint.setVisibility(View.INVISIBLE);
            lv_search_result.setVisibility(View.INVISIBLE);
            tv_no_conn_hint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onInternetDisconnect() {
        tv_hint.setVisibility(View.INVISIBLE);
        lv_search_result.setVisibility(View.INVISIBLE);
        tv_no_conn_hint.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInternetConnect() {
        tv_no_conn_hint.setVisibility(View.INVISIBLE);
        if (this.adapter.getCount() == 0) {
            lv_search_result.setVisibility(View.INVISIBLE);
            tv_hint.setVisibility(View.VISIBLE);
        } else {
            lv_search_result.setVisibility(View.VISIBLE);
            tv_hint.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public String getFragmentLabel(){
        return "Search";
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}
