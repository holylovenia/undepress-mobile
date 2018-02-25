package com.hulahoop.mentalhealth.undepress;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by holy on 10/02/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        System.out.println("Pager Adapter Created");
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println(position);
        switch (position) {
            case 0:
                return new DetectFragment();
            case 1:
                return new MonitorFragment();
            case 2:
                return new ExpertsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
