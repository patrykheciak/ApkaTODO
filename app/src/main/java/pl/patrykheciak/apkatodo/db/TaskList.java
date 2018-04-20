package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    private long id_tasklist;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "color")
    private String color;

    public long getId_tasklist() {
        return id_tasklist;
    }

    public void setId_tasklist(long id_tasklist) {
        this.id_tasklist = id_tasklist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}