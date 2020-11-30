package com.datafrey.goalsmanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.data.GoalsRepository;

public class ArchiveViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    public LiveData<Boolean> getArchiveIsEmptyCheckResult() {
        return goalsRepository.getArchiveIsEmptyCheckResult();
    }

    public void uiReactedToArchiveIsEmptyCheckResult() {
        goalsRepository.setArchiveIsEmptyCheckResultToDefaultValue();
    }

    public LiveData<Boolean> getArchiveCleaningResult() {
        return goalsRepository.getArchiveCleaningSuccess();
    }

    public void uiReactedToArchiveCleaningResult() {
        goalsRepository.setArchiveCleaningSuccessValueToDefault();
    }

    public ArchiveViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(getApplication());
    }

    public void checkArchiveIsEmpty() {
        goalsRepository.checkArchiveIsEmpty();
    }

    public void cleanArchive() {
        goalsRepository.cleanArchive();
    }
}
