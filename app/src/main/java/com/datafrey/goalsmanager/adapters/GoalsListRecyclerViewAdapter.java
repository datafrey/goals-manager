package com.datafrey.goalsmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.data.Goal;
import com.datafrey.goalsmanager.databinding.GoalItemBinding;
import com.datafrey.goalsmanager.vieweventlisteners.GoalItemViewEventListener;

import java.util.List;

public class GoalsListRecyclerViewAdapter
        extends RecyclerView.Adapter<GoalsListRecyclerViewAdapter.GoalViewHolder> {

    private List<Goal> goalsList;
    private GoalItemViewEventListener itemViewEventListener;

    public void setGoalsList(List<Goal> goalsList) {
        this.goalsList = goalsList;
    }

    public void setItemViewEventListener(GoalItemViewEventListener itemViewEventListener) {
        this.itemViewEventListener = itemViewEventListener;
    }

    public GoalsListRecyclerViewAdapter(List<Goal> goalsList) {
        this.goalsList = goalsList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal currentGoal = goalsList.get(position);
        holder.getBinding().setGoal(currentGoal);
        holder.getBinding().setEventListener(itemViewEventListener);
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {

        private GoalItemBinding binding;

        public GoalItemBinding getBinding() {
            return binding;
        }

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GoalItemBinding.bind(itemView);
        }
    }
}
