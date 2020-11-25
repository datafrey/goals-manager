package com.datafrey.goalsmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.datafrey.goalsmanager.R;
import com.datafrey.goalsmanager.activities.AddGoalActivity;
import com.datafrey.goalsmanager.adapters.DeadlinePageAdapter;
import com.google.android.material.tabs.TabLayout;

public class GoalsFragment extends Fragment {

    private TabLayout goalsDeadlineTabLayout;
    private ViewPager goalsDeadlineViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        goalsDeadlineTabLayout = view.findViewById(R.id.goalsDeadlineTabLayout);
        goalsDeadlineViewPager = view.findViewById(R.id.goalsDeadlineViewPager);

        setupViewPagerAndTabLayout();

        view.findViewById(R.id.addGoalButton).setOnClickListener(button -> openAddGoalActivity());

        return view;
    }

    private void setupViewPagerAndTabLayout() {
        DeadlinePageAdapter deadlinePageAdapter =
                new DeadlinePageAdapter(getChildFragmentManager(), goalsDeadlineTabLayout.getTabCount());
        goalsDeadlineViewPager.setAdapter(deadlinePageAdapter);

        goalsDeadlineTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goalsDeadlineViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        goalsDeadlineViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                goalsDeadlineTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void openAddGoalActivity() {
        requireActivity().startActivity(new Intent(requireContext(), AddGoalActivity.class));
    }
}
