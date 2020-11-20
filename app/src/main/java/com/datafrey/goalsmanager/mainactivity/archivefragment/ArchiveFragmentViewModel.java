package com.datafrey.goalsmanager.mainactivity.archivefragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.datafrey.goalsmanager.data.GoalsRepository;

public class ArchiveFragmentViewModel extends AndroidViewModel {

    private GoalsRepository goalsRepository;

    public LiveData<Boolean> getArchiveCleaningResult() {
        return goalsRepository.getCleanArchiveSuccess();
    }

    public void uiReactedToArchiveCleaningResult() {
        goalsRepository.setCleanArchiveSuccessValueToDefault();
    }

    public boolean archiveIsEmpty() {
        if (goalsRepository.getArchiveGoals().getValue() == null) {
            return true;
        }

        return goalsRepository.getArchiveGoals().getValue().isEmpty();
    }

    public ArchiveFragmentViewModel(@NonNull Application application) {
        super(application);
        goalsRepository = new GoalsRepository(getApplication());
    }

    public void cleanArchive() {
        goalsRepository.cleanArchive();
    }
}
