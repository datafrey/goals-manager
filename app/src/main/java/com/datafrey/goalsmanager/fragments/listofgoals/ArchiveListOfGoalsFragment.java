package com.datafrey.goalsmanager.fragments.listofgoals;

import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.viewmodelfactories.ListOfGoalsViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ArchiveListOfGoalsViewModel;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsViewModel;

public class ArchiveListOfGoalsFragment extends ListOfGoalsFragment {

    @Override
    protected PlaceholderType setPlaceholderType() {
        return PlaceholderType.ARCHIVE;
    }

    @Override
    protected ListOfGoalsViewModel setViewModel() {
        return new ViewModelProvider(
                getActivity(),
                new ListOfGoalsViewModelFactory(getActivity().getApplication())
        ).get(ArchiveListOfGoalsViewModel.class);
    }
}
