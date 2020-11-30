package com.datafrey.goalsmanager.viewmodelfactories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodels.listofgoals.ArchiveListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.MonthListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.MoreListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextMonthListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextWeekListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextYearListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.TodayListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.WeekListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.YearListOfGoalsViewModel;

public class ListOfGoalsViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ListOfGoalsViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodayListOfGoalsViewModel.class)) {
            return (T) new TodayListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(WeekListOfGoalsViewModel.class)) {
            return (T) new WeekListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(NextWeekListOfGoalsViewModel.class)) {
            return (T) new NextWeekListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(MonthListOfGoalsViewModel.class)) {
            return (T) new MonthListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(NextMonthListOfGoalsViewModel.class)) {
            return (T) new NextMonthListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(YearListOfGoalsViewModel.class)) {
            return (T) new YearListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(NextYearListOfGoalsViewModel.class)) {
            return (T) new NextYearListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(MoreListOfGoalsViewModel.class)) {
            return (T) new MoreListOfGoalsViewModel(application);
        } else if (modelClass.isAssignableFrom(ArchiveListOfGoalsViewModel.class)) {
            return (T) new ArchiveListOfGoalsViewModel(application);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
