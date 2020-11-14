package com.datafrey.goalsmanager.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "goals")
public class Goal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0L;

    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "description")
    private String description = "";

    @ColumnInfo(name = "category")
    private String category = "None";

    @ColumnInfo(name = "deadline_date")
    @TypeConverters({DateConverter.class})
    private Date deadlineDate = Calendar.getInstance().getTime();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
