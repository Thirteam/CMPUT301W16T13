package cmput301.textbookhub.Views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmput301.textbookhub.R;

/**
 * Created by Fred on 2016/2/29.
 */
public class Fragment_UserMain extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_usermain, null);

        return v;
    }

    public static String getFragmentLabel(){
        return "User";
    }
}
