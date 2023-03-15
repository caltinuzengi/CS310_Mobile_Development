package com.example.trail_01;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    int tabPosition;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabPosition = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new EconomicsFragment();
            case 1:
                return new SportsFragment();
            case 2:
                return new PoliticsFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabPosition;
    }
}
