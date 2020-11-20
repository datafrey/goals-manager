package com.datafrey.goalsmanager.mainactivity.archivefragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.datafrey.goalsmanager.data.DeadlineType;
import com.datafrey.goalsmanager.mainactivity.goalslistfragment.GoalsListFragment;
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
                new GoalsListFragment(DeadlineType.ARCHIVE)
        ).commit();

        FloatingActionButton cleanArchiveButton = view.findViewById(R.id.cleanArchiveButton);

        viewModel = new ViewModelProvider(
                this,
                new ArchiveFragmentViewModelFactory(getActivity().getApplication())
        ).get(ArchiveFragmentViewModel.class);

        cleanArchiveButton.setOnClickListener(v -> onCleanArchiveButtonClick());

        viewModel.getArchiveCleaningResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                Toast.makeText(
                        getActivity(),
                        success ? "Archive successfully cleaned!" : "Something went wrong...",
                        Toast.LENGTH_SHORT
                ).show();

                viewModel.uiReactedToArchiveCleaningResult();
            }
        });

        return view;
    }

    private void onCleanArchiveButtonClick() {
        if (!viewModel.archiveIsEmpty()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you sure you want to clean your archive?")
                    .setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        viewModel.cleanArchive();
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel())
                    .show();
        } else {
            Toast.makeText(getActivity(), "Archive is already empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
