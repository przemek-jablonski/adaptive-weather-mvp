package com.android.szparag.newadaptiveweather.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ciemek on 14/10/2016.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static int ITEM_COUNT = 5;
    //1: bulk info
    //2: day +1 (tomorrow)
    //3: day +2 (next day) etc etc


    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return BulkWeatherInfoFragment.newInstance(position, "");
        }
        return OneDayWeatherInfoFragment.newInstance(position, Integer.toString(position));
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Today";
        }
        if (position == 1) {
            return "Tomorrow"; //todo: make this load from strings.xml
        }
        return ("Day +" + position);
    }
}
