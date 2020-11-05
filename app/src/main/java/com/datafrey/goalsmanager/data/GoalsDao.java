package com.datafrey.goalsmanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalsDao {

    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Query("SELECT * FROM goals WHERE id = :id")
    Goal get(long id);

    @Query("DELETE FROM goals WHERE id = :id")
    void delete(long id);

    @Query("DELETE FROM goals")
    void clear();

    @Query("SELECT * FROM goals WHERE deadline_date = date('now')")
    LiveData<List<Goal>> getTodayGoals();

    @Query("SELECT * FROM goals WHERE deadline_date BETWEEN date('now', '+1 day') AND date('now', '+6 days')")
    LiveData<List<Goal>> getThisWeekGoals();

    @Query("SELECT * FROM goals WHERE deadline_date BETWEEN date('now', '+1 day') AND date('now', '+1 month', '-1 day')")
    LiveData<List<Goal>> getThisMonthGoals();

    @Query("SELECT * FROM goals WHERE deadline_date BETWEEN date('now', '+1 day') AND date('now', '+1 year', '-1 day')")
    LiveData<List<Goal>> getThisYearGoals();

    @Query("SELECT * FROM goals WHERE deadline_date BETWEEN date('1980-12-31') AND date('now')")
    LiveData<List<Goal>> getArchiveGoals();
}
