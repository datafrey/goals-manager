package com.datafrey.goalsmanager.fragments.listofgoals;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.activities.EditGoalActivity;
import com.datafrey.goalsmanager.activities.MainActivity;
import com.datafrey.goalsmanager.adapters.GoalsListRecyclerViewAdapter;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.util.RecyclerViewBottomOffsetDecoration;
import com.datafrey.goalsmanager.vieweventlisteners.GoalItemViewEventListener;
import com.datafrey.goalsmanager.viewmodels.listofgoals.ListOfGoalsFragmentViewModel;

import java.util.List;

public abstract class ListOfGoalsFragment extends Fragment {

    protected MainActivity activity;

    protected RecyclerView goalsListRecyclerView;
    protected TextView placeholderTextView;

    protected PlaceholderType placeholderType;

    protected abstract PlaceholderType setPlaceholderType();

    protected ListOfGoalsFragmentViewModel viewModel;

    protected abstract ListOfGoalsFragmentViewModel setViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals_list, container, false);

        goalsListRecyclerView = view.findViewById(R.id.goalsListRecyclerView);
        placeholderTextView = view.findViewById(R.id.placeholderTextView);

        placeholderType = setPlaceholderType();
        viewModel = setViewModel();

        setupPlaceholder();

        viewModel.getGoalsToDisplay().observe(getViewLifecycleOwner(), this::updateRecyclerView);
        viewModel.getGoalDeletionResult().observe(getViewLifecycleOwner(), this::reactToGoalDeletionResult);

        goalsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewBottomOffsetDecoration bottomOffsetDecoration =
                new RecyclerViewBottomOffsetDecoration(200);
        goalsListRecyclerView.addItemDecoration(bottomOffsetDecoration);

        return view;
    }

    private void setupPlaceholder() {
        switch (placeholderType) {
            case TODAY:
                placeholderTextView.setText("No goals for today.");
                break;
            case ARCHIVE:
                placeholderTextView.setText("No goals in archive.");
                break;
            default:
                placeholderTextView.setText("No goals here. Perhaps they migrated to the previous tab.");
        }

        viewModel.getPlaceholderVisibility().observe(getViewLifecycleOwner(),
                isVisible -> placeholderTextView.setVisibility(isVisible ? View.VISIBLE : View.GONE));
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
            viewModel.setPlaceholderVisibility(goalsList.isEmpty());
        } else {
            viewModel.setPlaceholderVisibility(true);
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

    private void reactToGoalDeletionResult(Boolean success) {
        if (success != null) {
            Toast.makeText(
                    activity,
                    success ? "Goal successfully deleted!" : "Something went wrong...",
                    Toast.LENGTH_SHORT
            ).show();

            viewModel.uiReactedToGoalDeletionResult();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
}
