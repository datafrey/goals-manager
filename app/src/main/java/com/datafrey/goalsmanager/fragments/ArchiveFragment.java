package com.datafrey.goalsmanager.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.fragments.listofgoals.ArchiveListOfGoalsFragment;
import com.datafrey.goalsmanager.viewmodelfactories.ArchiveFragmentViewModelFactory;
import com.datafrey.goalsmanager.viewmodels.ArchiveFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArchiveFragment extends Fragment {

    private ArchiveFragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().add(
                R.id.fragmentContainerForArchiveGoalsList,
                new ArchiveListOfGoalsFragment()
        ).commit();

        FloatingActionButton cleanArchiveButton = view.findViewById(R.id.cleanArchiveButton);

        viewModel = new ViewModelProvider(
                getActivity(),
                new ArchiveFragmentViewModelFactory(getActivity().getApplication())
        ).get(ArchiveFragmentViewModel.class);

        cleanArchiveButton.setOnClickListener(button -> viewModel.checkArchiveIsEmpty());

        viewModel.getArchiveIsEmptyCheckResult().observe(getViewLifecycleOwner(), this::reactToArchiveIsEmptyCheckResult);
        viewModel.getArchiveCleaningResult().observe(getViewLifecycleOwner(), this::reactToArchiveCleaningResult);

        return view;
    }

    private void reactToArchiveIsEmptyCheckResult(Boolean archiveIsEmpty) {
        if (archiveIsEmpty != null) {
            if (archiveIsEmpty) {
                Toast.makeText(getActivity(), "Archive is already empty!", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to clean your archive?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.cleanArchive();
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .show();
            }

            viewModel.uiReactedToArchiveIsEmptyCheckResult();
        }
    }

    private void reactToArchiveCleaningResult(Boolean success) {
        if (success != null) {
            Toast.makeText(
                    getActivity(),
                    success ? "Archive successfully cleaned!" : "Something went wrong...",
                    Toast.LENGTH_SHORT
            ).show();

            viewModel.uiReactedToArchiveCleaningResult();
        }
    }
}
