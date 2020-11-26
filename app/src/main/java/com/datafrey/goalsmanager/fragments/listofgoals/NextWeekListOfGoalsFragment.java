package com.datafrey.goalsmanager.fragments.listofgoals;

import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodelfactories.ListOfGoalsFragmentViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.NextWeekListOfGoalsFragmentViewModel;

public class NextWeekListOfGoalsFragment extends ListOfGoalsFragment {

    @Override
    protected PlaceholderType setPlaceholderType() {
        return PlaceholderType.OTHER;
    }

    @Override
    protected ListOfGoalsFragmentViewModel setViewModel() {
        return new ViewModelProvider(
                getActivity(),
                new ListOfGoalsFragmentViewModelFactory(getActivity().getApplication())
        ).get(NextWeekListOfGoalsFragmentViewModel.class);
    }
}
