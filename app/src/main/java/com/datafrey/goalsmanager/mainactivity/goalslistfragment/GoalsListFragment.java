package com.datafrey.goalsmanager.mainactivity.goalslistfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.data.DeadlineType;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.editgoalactivity.EditGoalActivity;
import com.datafrey.goalsmanager.mainactivity.MainActivity;
import com.datafrey.goalsmanager.util.RecyclerViewBottomOffsetDecoration;

import java.util.List;

public class GoalsListFragment extends Fragment {

    private MainActivity activity;

    private DeadlineType deadlineType;
    private boolean addBottomOffsetDecoration;

    private RecyclerView goalsListRecyclerView;

    private GoalsListFragmentViewModel viewModel;

    public GoalsListFragment() {
        this.deadlineType = DeadlineType.TODAY;
        addBottomOffsetDecoration = true;
    }

    public GoalsListFragment(DeadlineType deadlineType) {
        this.deadlineType = deadlineType;
        addBottomOffsetDecoration = true;
    }

    public GoalsListFragment(DeadlineType deadlineType, boolean addBottomOffsetDecoration) {
        this.deadlineType = deadlineType;
        this.addBottomOffsetDecoration = addBottomOffsetDecoration;
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

        if (addBottomOffsetDecoration) {
            RecyclerViewBottomOffsetDecoration bottomOffsetDecoration =
                    new RecyclerViewBottomOffsetDecoration(200);
            goalsListRecyclerView.addItemDecoration(bottomOffsetDecoration);
        }

        return view;
    }

    private void updateRecyclerView(List<Goal> goalsList) {
        if (viewModel.getGoalsListRecyclerViewAdapter() == null) {
            GoalsListRecyclerViewAdapter recyclerViewAdapter = new GoalsListRecyclerViewAdapter(goalsList);
            recyclerViewAdapter.setItemViewEventListener(buildGoalItemViewEventListener());
            viewModel.setGoalsListRecyclerViewAdapter(recyclerViewAdapter);
        } else {
            viewModel.getGoalsListRecyclerViewAdapter().setGoalsList(goalsList);
        }

        if (goalsListRecyclerView.getAdapter() == null) {
            goalsListRecyclerView.setAdapter(viewModel.getGoalsListRecyclerViewAdapter());
        }

        viewModel.getGoalsListRecyclerViewAdapter().notifyDataSetChanged();
    }

    private GoalItemViewEventListener buildGoalItemViewEventListener() {
        return new GoalItemViewEventListener() {
            @Override
            public void onItemViewClick(View view) {
            }

            @Override
            public void onEditButtonClick(View view, Goal goal) {
                ImageButton editGoalButton = (ImageButton) view;
                ImageButton deleteGoalButton = view.getRootView().findViewById(R.id.deleteGoalButton);

                editGoalButton.setEnabled(false);
                deleteGoalButton.setEnabled(false);

                Intent editIntent = new Intent(activity, EditGoalActivity.class);
                editIntent.putExtra("goalId", goal.getId());
                activity.startActivity(editIntent);

                editGoalButton.setEnabled(true);
                deleteGoalButton.setEnabled(true);
            }

            @Override
            public void onDeleteButtonClick(View view, Goal goal) {
            }
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
}
