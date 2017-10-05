package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.apps.restclienttemplate.utils.SmartFragmentStatePagerAdapter;

/**
 * Created by jason on 10/4/17.
 */

public class TweetsPageAdapter extends SmartFragmentStatePagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;

    public TweetsPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
