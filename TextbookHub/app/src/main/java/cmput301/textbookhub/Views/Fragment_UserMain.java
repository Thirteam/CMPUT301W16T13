package cmput301.textbookhub.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MainActivityController;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;

/**
 * Created by Fred on 2016/2/29.
 */
public class Fragment_UserMain extends BaseFragment implements NetworkStateObserver{

    private LinearLayout btn_profile, btn_inventory, btn_bids, btn_borrows;
    private Button btn_logout;
    AppUserController userController;
    MainActivityController activityController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.frag_usermain, null);
        userController = AppUserController.getInstance();
        btn_profile = (LinearLayout) v.findViewById(R.id.button_userprofile);
        btn_inventory = (LinearLayout) v.findViewById(R.id.button_myinventory);
        btn_bids = (LinearLayout) v.findViewById(R.id.button_mybids);
        btn_borrows = (LinearLayout) v.findViewById(R.id.button_myborrows);
        btn_logout = (Button) v.findViewById(R.id.button_logout);
        activityController = ((Activity_Main)getActivity()).getActivityController();
        toggleView();
        btn_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(), Activity_UserProfile.class);
                Bundle b = new Bundle();
                b.putString(Activity_UserProfile.BUNDLE_KEY_PROFILE_TYPE, Activity_UserProfile.BUNDLE_CONTENT_ACTIVITY_TYPE_VIEW);
                intent.putExtra(Activity_UserProfile.INTENT_EXTRAS_KEY_BUNDLE, b);
                startActivity(intent);
            }
        });
        btn_borrows.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (userController.hasServerAccess() && !userController.hasServerAccess()) {
                    activityController.displayNotificationDialog(getContext(), getContext().getResources().getString(R.string.error), getContext().getResources().getString(R.string.offline_no_server));
                }else if(!userController.hasInternetAccess(getContext())){
                    activityController.displayNotificationDialog(getContext(), getContext().getResources().getString(R.string.error), getContext().getResources().getString(R.string.offline_not_available));
                }else{
                    Intent intent = new Intent(v.getContext(), Activity_MyBorrows.class);
                    startActivity(intent);
                }
            }
        });
        btn_inventory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (userController.hasServerAccess() && !userController.hasServerAccess()) {
                    activityController.displayNotificationDialog(getContext(), getContext().getResources().getString(R.string.error), getContext().getResources().getString(R.string.offline_no_server));
                }else {
                    Intent intent = new Intent(v.getContext(), Activity_MyInventory.class);
                    startActivity(intent);
                }
            }
        });
        btn_bids.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (userController.hasServerAccess() && !userController.hasServerAccess()) {
                    activityController.displayNotificationDialog(getContext(), getContext().getResources().getString(R.string.error), getContext().getResources().getString(R.string.offline_no_server));
                }else if(!userController.hasInternetAccess(getContext())){
                    activityController.displayNotificationDialog(getContext(), getContext().getResources().getString(R.string.error), getContext().getResources().getString(R.string.offline_not_available));
                }else {
                    Intent intent = new Intent(v.getContext(), Activity_MyBids.class);
                    startActivity(intent);
                }

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle(getResources().getString(R.string.title_logout));
                dialog.setMessage(getResources().getString(R.string.logout_warning));
                dialog.setPositiveButton(getResources().getString(R.string.yes_en), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), Activity_Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        userController.clearAppUser();
                        userController.saveOfflineUserProfile();
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                });
                dialog.setNegativeButton(getResources().getString(R.string.no_en), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    public void toggleView(){
        if(userController.hasInternetAccess(getContext())) {
            btn_bids.setEnabled(true);
            btn_borrows.setEnabled(true);
        }else{
            btn_bids.setEnabled(false);
            btn_borrows.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onInternetConnect() {
        btn_bids.setEnabled(true);
        btn_borrows.setEnabled(true);
    }

    @Override
    public void onInternetDisconnect() {
        btn_bids.setEnabled(false);
        btn_borrows.setEnabled(false);
    }

    @Override
    public String getFragmentLabel(){
        return "User";
    }
}
