package com.datafrey.goalsmanager.mainactivity.archivefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.data.DeadlineType;
import com.datafrey.goalsmanager.mainactivity.goalslistfragment.GoalsListFragment;

public class ArchiveFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().add(
                R.id.fragmentContainerForArchiveGoalsList,
                new GoalsListFragment(DeadlineType.ARCHIVE, false)
        ).commit();

        return view;
    }
}
