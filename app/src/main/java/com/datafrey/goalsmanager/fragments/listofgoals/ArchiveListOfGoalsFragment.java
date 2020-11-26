package com.datafrey.goalsmanager.fragments.listofgoals;

import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodelfactories.ListOfGoalsFragmentViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ArchiveListOfGoalsFragmentViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsFragmentViewModel;

public class ArchiveListOfGoalsFragment extends ListOfGoalsFragment {

    @Override
    protected PlaceholderType setPlaceholderType() {
        return PlaceholderType.ARCHIVE;
    }

    @Override
    protected ListOfGoalsFragmentViewModel setViewModel() {
        return new ViewModelProvider(
                getActivity(),
                new ListOfGoalsFragmentViewModelFactory(getActivity().getApplication())
        ).get(ArchiveListOfGoalsFragmentViewModel.class);
    }
}
