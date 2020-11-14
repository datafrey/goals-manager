package com.datafrey.goalsmanager.data;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @TypeConverter
    public static Date fromDatabaseString(String databaseString) {
        try {
            return databaseString == null ? null : formatter.parse(databaseString);
        } catch (ParseException parseException) {
            Log.e("DateConverterError", "Can't convert database string to date: " + parseException.getMessage());
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        return date == null ? null : formatter.format(date);
    }
}
