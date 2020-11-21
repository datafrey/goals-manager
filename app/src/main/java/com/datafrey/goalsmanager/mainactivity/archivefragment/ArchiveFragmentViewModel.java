package com.datafrey.goalsmanager.mainactivity.archivefragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.data.GoalsRepository;

public class ArchiveFragmentViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    public LiveData<Boolean> getArchiveIsEmptyCheckResult() {
        return goalsRepository.getArchiveIsEmptyCheckResult();
    }

    public void uiReactedToArchiveIsEmptyCheckResult() {
        goalsRepository.setArchiveIsEmptyCheckResultToDefaultValue();
    }

    public LiveData<Boolean> getArchiveCleaningResult() {
        return goalsRepository.getCleanArchiveSuccess();
    }

    public void uiReactedToArchiveCleaningResult() {
        goalsRepository.setCleanArchiveSuccessValueToDefault();
    }

    public ArchiveFragmentViewModel(@NonNull Application application) {
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
