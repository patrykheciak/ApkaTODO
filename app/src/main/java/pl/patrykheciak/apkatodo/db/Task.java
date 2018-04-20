package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = TaskSublist.class,
        parentColumns = "id_tasksublist",
        childColumns = "id_tasksublist"))
public class Task {


    @PrimaryKey(autoGenerate = true)
    private long id_task;

    @ColumnInfo(name = "id_tasksublist")
    private long id_tasksublist;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "priority")
    private boolean prioritiy;

    @ColumnInfo(name = "date")
    private Date reminderDate;

    public long getId_task() {
        return id_task;
    }

    public void setId_task(long id_task) {
        this.id_task = id_task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getPrioritiy() {
        return prioritiy;
    }

    public void setPrioritiy(boolean prioritiy) {
        this.prioritiy = prioritiy;
    }

    public long getId_tasksublist() {
        return id_tasksublist;
    }

    public void setId_tasksublist(long id_tasksublist) {
        this.id_tasksublist = id_tasksublist;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }
}
