package com.datafrey.goalsmanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.datafrey.goalsmanager.fragments.listofgoals.MonthListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.MoreListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.NextMonthListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.NextWeekListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.NextYearListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.TodayListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.WeekListOfGoalsFragment;
import com.datafrey.goalsmanager.fragments.listofgoals.YearListOfGoalsFragment;

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
                return new TodayListOfGoalsFragment();
            case 1:
                return new WeekListOfGoalsFragment();
            case 2:
                return new NextWeekListOfGoalsFragment();
            case 3:
                return new MonthListOfGoalsFragment();
            case 4:
                return new NextMonthListOfGoalsFragment();
            case 5:
                return new YearListOfGoalsFragment();
            case 6:
                return new NextYearListOfGoalsFragment();
            case 7:
                return new MoreListOfGoalsFragment();
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
