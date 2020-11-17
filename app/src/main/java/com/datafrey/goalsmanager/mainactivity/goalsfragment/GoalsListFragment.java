package com.datafrey.goalsmanager.mainactivity.goalsfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.util.RecyclerViewBottomOffsetDecoration;

import java.util.List;

public class GoalsListFragment extends Fragment {

    private DeadlineType deadlineType;

    private RecyclerView goalsListRecyclerView;

    private GoalsListFragmentViewModel viewModel;

    public GoalsListFragment() {
        this.deadlineType = DeadlineType.TODAY;
    }

    public GoalsListFragment(DeadlineType deadlineType) {
        this.deadlineType = deadlineType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals_list_tab, container, false);

        goalsListRecyclerView = view.findViewById(R.id.goalsListRecyclerView);

        viewModel = new ViewModelProvider(
                this,
                new GoalsListFragmentViewModelFactory(getActivity().getApplication(), deadlineType)
        ).get(GoalsListFragmentViewModel.class);

        viewModel.getGoalsToDisplay().observe(getViewLifecycleOwner(), this::updateRecyclerView);

        goalsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewBottomOffsetDecoration bottomOffsetDecoration =
                new RecyclerViewBottomOffsetDecoration(200);
        goalsListRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    private void updateRecyclerView(List<Goal> goalsList) {
        if (viewModel.getGoalsListRecyclerViewAdapter() == null) {
            viewModel.setGoalsListRecyclerViewAdapter(
                    new GoalsListRecyclerViewAdapter(goalsList));
        } else {
            viewModel.getGoalsListRecyclerViewAdapter().setGoalsList(goalsList);
        }

        if (goalsListRecyclerView.getAdapter() == null) {
            goalsListRecyclerView.setAdapter(viewModel.getGoalsListRecyclerViewAdapter());
        }

        viewModel.getGoalsListRecyclerViewAdapter().notifyDataSetChanged();
    }
}
