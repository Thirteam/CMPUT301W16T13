package cmput301.textbookhub.Views;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cmput301.textbookhub.Controllers.ActivityControllerFactory;
import cmput301.textbookhub.Controllers.AppUserController;
import cmput301.textbookhub.Controllers.MainActivityController;
import cmput301.textbookhub.R;
import cmput301.textbookhub.Receivers.NetworkStateObserver;
import cmput301.textbookhub.Receivers.NetworkStateManager;

public class Activity_Main extends AppCompatActivity implements BaseView, NetworkStateObserver{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MainActivityController activityController;
    private AppUserController userController;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activityController  = (MainActivityController) ActivityControllerFactory.getControllerForView(
                ActivityControllerFactory.FactoryCatalog.ACTIVITY_MAIN, this);
        this.userController = AppUserController.getInstance();

        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(viewPager);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.offline_title));
        b.setMessage(getResources().getString(R.string.offline_content));
        b.setPositiveButton(getResources().getString(R.string.ok_en), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = b.create();
    }

    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BaseFragment frag_search = new Fragment_Search();
        BaseFragment frag_main = new Fragment_UserMain();
        adapter.addFragment(frag_search, frag_search.getFragmentLabel());
        adapter.addFragment(frag_main, frag_main.getFragmentLabel());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    public MainActivityController getActivityController() {
        return activityController;
    }

    @Override
    public void updateView(){}

    @Override
    public void onInternetConnect() {
        dialog.dismiss();
    }

    @Override
    public void onInternetDisconnect() {
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userController.saveOfflineUserProfile();
        NetworkStateManager.getInstance().addViewObserver(this);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //userController.saveOfflineCommands();
        userController.saveOfflineUserProfile();
    }
}
