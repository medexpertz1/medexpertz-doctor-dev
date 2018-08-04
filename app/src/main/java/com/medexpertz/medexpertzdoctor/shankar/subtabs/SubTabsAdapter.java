package com.medexpertz.medexpertzdoctor.shankar.subtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/10/2015.
 */
public class SubTabsAdapter extends FragmentPagerAdapter {

    private ArrayList<SubTabDetails> items;

    public SubTabsAdapter(FragmentManager fm, ArrayList<SubTabDetails> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

}