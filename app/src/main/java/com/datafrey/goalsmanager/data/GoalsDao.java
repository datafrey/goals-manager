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

    @Query("SELECT * FROM goals")
    LiveData<List<Goal>> getAllGoals();

    @Query("DELETE FROM goals")
    void clear();

    @Query("SELECT * FROM goals WHERE date(deadline_date) = date('now')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getTodayGoals();

    @Query("SELECT * FROM goals WHERE " +
            "(date(deadline_date) BETWEEN date('now', '+1 day') AND date('now', 'weekday 6', '+1 days'))" +
            " AND " +
            "NOT ((date(deadline_date) = date('now')) AND (date(deadline_date) = date('now', 'weekday 6', '+1 days')))" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getWeekGoals();

    @Query("SELECT * FROM goals WHERE " +
            "date(deadline_date) BETWEEN date('now', 'weekday 6', '+2 days') AND date('now', 'weekday 6', '+8 days')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getNextWeekGoals();

    @Query("SELECT * FROM goals WHERE " +
            "(date(deadline_date) BETWEEN date('now', '+1 day') AND date('now', 'start of month', '+1 month', '-1 day'))" +
            " AND " +
            "NOT ((date(deadline_date) = date('now')) AND (date(deadline_date) = date('now', 'start of month', '+1 month', '-1 day')))" +
            " AND " +
            "NOT (date(deadline_date) BETWEEN date('now', '+1 day') AND date('now', 'weekday 6', '+8 days'))" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getMonthGoals();

    @Query("SELECT * FROM goals WHERE " +
            "date(deadline_date) BETWEEN date('now', 'start of month', '+1 month') AND date('now', 'start of month', '+2 month', '-1 day')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getNextMonthGoals();

    @Query("SELECT * FROM goals WHERE " +
            "(date(deadline_date) BETWEEN date('now', '+1 day') AND date('now', 'start of year', '+1 year', '-1 day'))" +
            " AND " +
            "NOT ((date(deadline_date) = date('now')) AND (date(deadline_date) = date('now', 'start of year', '+1 year', '-1 day')))" +
            " AND " +
            "NOT (date(deadline_date) BETWEEN date('now', '+1 day') AND date('now', 'start of month', '+2 month', '-1 day'))" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getYearGoals();

    @Query("SELECT * FROM goals WHERE " +
            "date(deadline_date) BETWEEN date('now', 'start of year', '+1 year') AND date('now', 'start of year', '+2 year', '-1 day')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getNextYearGoals();

    @Query("SELECT * FROM goals WHERE " +
            "date(deadline_date) >= date('now', 'start of year', '+2 year')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getLongTermGoals();

    @Query("SELECT * FROM goals WHERE date(deadline_date) < date('now')" +
            " ORDER BY date(deadline_date)")
    LiveData<List<Goal>> getArchiveGoals();
}
