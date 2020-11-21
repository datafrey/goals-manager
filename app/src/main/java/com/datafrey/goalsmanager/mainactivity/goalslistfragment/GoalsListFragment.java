package com.datafrey.goalsmanager.mainactivity.goalslistfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private RecyclerView goalsListRecyclerView;
    private TextView placeholderTextView;

    private GoalsListFragmentViewModel viewModel;

    public GoalsListFragment() {
        this.deadlineType = null;
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
        placeholderTextView = view.findViewById(R.id.placeholderTextView);

        viewModel = new ViewModelProvider(
                this,
                new GoalsListFragmentViewModelFactory(getActivity().getApplication(), deadlineType)
        ).get(GoalsListFragmentViewModel.class);

        if (deadlineType == null) {
            deadlineType = viewModel.getDeadlineType();
        }

        switch (deadlineType) {
            case TODAY:
                placeholderTextView.setText("No goals for today.");
                break;
            case ARCHIVE:
                placeholderTextView.setText("No goals in archive.");
                break;
            default:
                placeholderTextView.setText("No goals here. Perhaps they migrated to the previous tab.");
        }

        viewModel.getGoalsToDisplay().observe(getViewLifecycleOwner(), this::updateRecyclerView);

        viewModel.getPlaceholderIsVisible().observe(getViewLifecycleOwner(),
                isVisible -> placeholderTextView.setVisibility(isVisible ? View.VISIBLE : View.GONE));

        viewModel.getGoalDeletionResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                Toast.makeText(
                        activity,
                        success ? "Goal successfully deleted!" : "Something went wrong...",
                        Toast.LENGTH_SHORT
                ).show();

                viewModel.uiReactedToGoalDeletionResult();
            }
        });

        goalsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewBottomOffsetDecoration bottomOffsetDecoration =
                new RecyclerViewBottomOffsetDecoration(200);
        goalsListRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    private void updateRecyclerView(List<Goal> goalsList) {
        if (goalsList != null) {
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
            viewModel.setPlaceholderIsVisible(goalsList.isEmpty());
        } else {
            viewModel.setPlaceholderIsVisible(true);
        }
    }

    private GoalItemViewEventListener buildGoalItemViewEventListener() {
        return new GoalItemViewEventListener() {
            @Override
            public void onItemViewClick(View view) {
            }

            @Override
            public void onEditButtonClick(View view, Goal goal) {
                Intent editIntent = new Intent(activity, EditGoalActivity.class);
                editIntent.putExtra("goalId", goal.getId());
                activity.startActivity(editIntent);
            }

            @Override
            public void onDeleteButtonClick(View view, Goal goal) {
                new AlertDialog.Builder(activity)
                        .setMessage("Are you sure you want to delete this goal?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.deleteGoal(goal);
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .show();
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
