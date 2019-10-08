package com.DataAquisition.Alpha1.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.DataAquisition.Alpha1.Page2_Fragment;
import com.DataAquisition.Alpha1.HelperClasses.DataConnector;
import com.DataAquisition.Alpha1.Page1_Fragment;
import com.DataAquisition.Alpha1.R;

public class HomeFragment extends Fragment {

    public static ScreenSlidePagerAdapter screenSlidePagerAdapter = null;
    public static DataConnector dataConnector = null;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity){
        myContext = (FragmentActivity)activity;
        super.onAttach(activity);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager mPager = (ViewPager)root.findViewById(R.id.pager);
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(myContext.getSupportFragmentManager(),this );
        mPager.setAdapter(screenSlidePagerAdapter);
        return root;
    }
}

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    public static Page1_Fragment page1_fragment_Instance = null;
    public static Page2_Fragment page2_fragment_Instance = null;
    private static HomeFragment mainActivity = null;
    public ScreenSlidePagerAdapter(FragmentManager fm, HomeFragment mainActivity) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            page1_fragment_Instance = new Page1_Fragment();
            this.mainActivity.dataConnector.page1_fragment = page1_fragment_Instance;
            return page1_fragment_Instance;

        }
        else if(position ==1)
        {
            page2_fragment_Instance = new Page2_Fragment();
            this.mainActivity.dataConnector.chartFragment = page2_fragment_Instance;
            return page2_fragment_Instance;
        }
        page1_fragment_Instance = new Page1_Fragment();
        return page1_fragment_Instance;
    }

    @Override
    public int getCount() {
        return 2;
    }
}