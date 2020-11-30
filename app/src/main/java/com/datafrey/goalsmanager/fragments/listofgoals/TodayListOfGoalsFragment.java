package com.datafrey.goalsmanager.fragments.listofgoals;

import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodelfactories.ListOfGoalsViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.TodayListOfGoalsViewModel;

public class TodayListOfGoalsFragment extends ListOfGoalsFragment {

    @Override
    protected PlaceholderType setPlaceholderType() {
        return PlaceholderType.TODAY;
    }

    @Override
    protected ListOfGoalsViewModel setViewModel() {
        return new ViewModelProvider(
                getActivity(),
                new ListOfGoalsViewModelFactory(getActivity().getApplication())
        ).get(TodayListOfGoalsViewModel.class);
    }
}
