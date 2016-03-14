package cmput301.textbookhub.Views;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cmput301.textbookhub.BaseApplication;
import cmput301.textbookhub.Controllers.ControllerFactory;
import cmput301.textbookhub.Controllers.MainActivityController;
import cmput301.textbookhub.R;

/**
 *
 *
 * @author CMPUT301W16T13
 * @Version 1.0
 * @since 2016-03-10
 */
public class Activity_Main extends AppCompatActivity implements BaseView{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.controller  = (MainActivityController) ControllerFactory.getControllerForView(
                ControllerFactory.FactoryCatalog.ACTIVITY_MAIN, this, ((BaseApplication)getApplication()).getAppUsername());

        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BaseFragment frag_search = new Fragment_Search();
        BaseFragment frag_main = new Fragment_UserMain();
        adapter.addFragment(frag_search, frag_search.getFragmentLabel());
        adapter.addFragment(frag_main, frag_main.getFragmentLabel());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    public MainActivityController getController() {
        return controller;
    }

    @Override
    public void updateView(){

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
}
