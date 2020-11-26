package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.listofgoals.ArchiveListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.MonthListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.MoreListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextMonthListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextWeekListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextYearListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.TodayListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.WeekListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.YearListOfGoalsFragmentViewModel;

public class ListOfGoalsFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ListOfGoalsFragmentViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodayListOfGoalsFragmentViewModel.class)) {
            return (T) new TodayListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(WeekListOfGoalsFragmentViewModel.class)) {
            return (T) new WeekListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(NextWeekListOfGoalsFragmentViewModel.class)) {
            return (T) new NextWeekListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(MonthListOfGoalsFragmentViewModel.class)) {
            return (T) new MonthListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(NextMonthListOfGoalsFragmentViewModel.class)) {
            return (T) new NextWeekListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(YearListOfGoalsFragmentViewModel.class)) {
            return (T) new YearListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(NextYearListOfGoalsFragmentViewModel.class)) {
            return (T) new NextYearListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(MoreListOfGoalsFragmentViewModel.class)) {
            return (T) new MoreListOfGoalsFragmentViewModel(application);
        } else if (modelClass.isAssignableFrom(ArchiveListOfGoalsFragmentViewModel.class)) {
            return (T) new ArchiveListOfGoalsFragmentViewModel(application);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
