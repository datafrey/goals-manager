package com.datafrey.goalsmanager.mainactivity.goalslistfragment;

import android.view.View;

import com.datafrey.goalsmanager.data.Goal;

public interface GoalItemViewEventListener {

    void onItemViewClick(View view);
    void onEditButtonClick(View view, Goal goal);
    void onDeleteButtonClick(View view, Goal goal);
}
