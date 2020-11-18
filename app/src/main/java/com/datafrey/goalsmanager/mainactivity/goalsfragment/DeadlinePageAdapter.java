package com.datafrey.goalsmanager.mainactivity.goalsfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.datafrey.goalsmanager.data.DeadlineType;
import com.datafrey.goalsmanager.mainactivity.goalslistfragment.GoalsListFragment;

public class DeadlinePageAdapter extends FragmentPagerAdapter {

    private final int numberOfTabs;

    public DeadlinePageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, numberOfTabs);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GoalsListFragment(DeadlineType.TODAY);
            case 1:
                return new GoalsListFragment(DeadlineType.WEEK);
            case 2:
                return new GoalsListFragment(DeadlineType.NEXT_WEEK);
            case 3:
                return new GoalsListFragment(DeadlineType.MONTH);
            case 4:
                return new GoalsListFragment(DeadlineType.NEXT_MONTH);
            case 5:
                return new GoalsListFragment(DeadlineType.YEAR);
            case 6:
                return new GoalsListFragment(DeadlineType.NEXT_YEAR);
            case 7:
                return new GoalsListFragment(DeadlineType.LONG_TERM);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
