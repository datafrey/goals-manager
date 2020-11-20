package com.datafrey.goalsmanager.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.datafrey.goalsmanager.util.TaskRunner;

import java.util.List;
import java.util.concurrent.Callable;

public class GoalsRepository {

    private final TaskRunner taskRunner;

    private final GoalsDao goalsDao;

    public LiveData<List<Goal>> getTodayGoals() {
        return goalsDao.getTodayGoals();
    }

    public LiveData<List<Goal>> getWeekGoals() {
        return goalsDao.getWeekGoals();
    }

    public LiveData<List<Goal>> getNextWeekGoals() {
        return goalsDao.getNextWeekGoals();
    }

    public LiveData<List<Goal>> getMonthGoals() {
        return goalsDao.getMonthGoals();
    }

    public LiveData<List<Goal>> getNextMonthGoals() {
        return goalsDao.getNextMonthGoals();
    }

    public LiveData<List<Goal>> getYearGoals() {
        return goalsDao.getYearGoals();
    }

    public LiveData<List<Goal>> getNextYearGoals() {
        return goalsDao.getNextYearGoals();
    }

    public LiveData<List<Goal>> getLongTermGoals() {
        return goalsDao.getLongTermGoals();
    }

    public LiveData<List<Goal>> getArchiveGoals() {
        return goalsDao.getArchiveGoals();
    }

    private final MutableLiveData<Boolean> goalInsertionSuccess = new MutableLiveData<>(null);
    public LiveData<Boolean> getGoalInsertionSuccess() {
        return goalInsertionSuccess;
    }

    public void setGoalInsertionSuccessValueToDefault() {
        goalInsertionSuccess.postValue(null);
    }

    private final MutableLiveData<Boolean> goalUpdateSuccess = new MutableLiveData<>(null);
    public LiveData<Boolean> getGoalUpdateSuccess() {
        return goalUpdateSuccess;
    }

    public void setGoalUpdateSuccessValueToDefault() {
        goalUpdateSuccess.postValue(null);
    }

    private final MutableLiveData<Goal> obtainedGoal = new MutableLiveData<>(null);
    public LiveData<Goal> getObtainedGoal() {
        return obtainedGoal;
    }

    public void setObtainedGoalValueToDefault() {
        obtainedGoal.postValue(null);
    }

    private final MutableLiveData<Boolean> goalDeleteSuccess = new MutableLiveData<>(null);
    public LiveData<Boolean> getGoalDeleteSuccess() {
        return goalDeleteSuccess;
    }

    public void setGoalDeleteSuccessValueToDefault() {
        goalDeleteSuccess.postValue(null);
    }

    private final MutableLiveData<Boolean> cleanArchiveSuccess = new MutableLiveData<>(null);
    public LiveData<Boolean> getCleanArchiveSuccess() {
        return cleanArchiveSuccess;
    }

    public void setCleanArchiveSuccessValueToDefault() {
        cleanArchiveSuccess.postValue(null);
    }

    public GoalsRepository(Application application) {
        taskRunner = new TaskRunner();

        GoalsDatabase database = GoalsDatabase.getInstance(application);
        goalsDao = database.goalsDao();
    }

    public void insertGoal(Goal goal) {
        taskRunner.executeAsync(new InsertGoalTask(goal), goalInsertionSuccess::postValue);
    }

    public void updateGoal(Goal goal) {
        taskRunner.executeAsync(new UpdateGoalTask(goal), goalUpdateSuccess::postValue);
    }

    public void getGoal(long id) {
        taskRunner.executeAsync(new GetGoalTask(id), obtainedGoal::postValue);
    }

    public void deleteGoal(long id) {
        taskRunner.executeAsync(new DeleteGoalTask(id), goalDeleteSuccess::postValue);
    }

    public void cleanArchive() {
        taskRunner.executeAsync(new CleanArchiveTask(), cleanArchiveSuccess::postValue);
    }

    private class InsertGoalTask implements Callable<Boolean> {

        private final Goal goal;

        public InsertGoalTask(Goal goal) {
            this.goal = goal;
        }

        @Override
        public Boolean call() {
            try {
                goalsDao.insert(goal);
                return true;
            } catch (Exception exception) {
                Log.e("InsertionError", exception.getMessage());
                return false;
            }
        }
    }

    private class UpdateGoalTask implements Callable<Boolean> {

        private final Goal goal;

        public UpdateGoalTask(Goal goal) {
            this.goal = goal;
        }

        @Override
        public Boolean call() {
            try {
                goalsDao.update(goal);
                return true;
            } catch (Exception exception) {
                Log.e("UpdatingError", exception.getMessage());
                return false;
            }
        }
    }

    private class GetGoalTask implements Callable<Goal> {

        private final long id;

        public GetGoalTask(long id) {
            this.id = id;
        }

        @Override
        public Goal call() {
            try {
                return goalsDao.get(id);
            } catch (Exception exception) {
                Log.e("ObtainingError", exception.getMessage());
                return null;
            }
        }
    }

    private class DeleteGoalTask implements Callable<Boolean> {

        private final long id;

        public DeleteGoalTask(long id) {
            this.id = id;
        }

        @Override
        public Boolean call() {
            try {
                goalsDao.delete(id);
                return true;
            } catch (Exception exception) {
                Log.e("DeletingError", exception.getMessage());
                return false;
            }
        }
    }

    private class CleanArchiveTask implements Callable<Boolean> {

        @Override
        public Boolean call() {
            try {
                goalsDao.cleanArchive();
                return true;
            } catch (Exception exception) {
                Log.e("CleaningArchiveError", exception.getMessage());
                return false;
            }
        }
    }
}
