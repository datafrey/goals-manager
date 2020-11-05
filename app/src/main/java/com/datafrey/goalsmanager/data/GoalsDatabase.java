package com.datafrey.goalsmanager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Goal.class}, version = 1, exportSchema = false)
public abstract class GoalsDatabase extends RoomDatabase {

    public abstract GoalsDao goalsDao();

    private static GoalsDatabase INSTANCE;

    public static GoalsDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (GoalsDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        GoalsDatabase.class, "goals_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return INSTANCE;
    }
}
