package com.datafrey.goalsmanager.fragments.listofgoals;

import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodelfactories.ListOfGoalsViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextWeekListOfGoalsViewModel;

public class NextWeekListOfGoalsFragment extends ListOfGoalsFragment {

    @Override
    protected PlaceholderType setPlaceholderType() {
        return PlaceholderType.OTHER;
    }

    @Override
    protected ListOfGoalsViewModel setViewModel() {
        return new ViewModelProvider(
                getActivity(),
                new ListOfGoalsViewModelFactory(getActivity().getApplication())
        ).get(NextWeekListOfGoalsViewModel.class);
    }
}
