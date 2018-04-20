package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = TaskList.class,
        parentColumns = "id_tasklist",
        childColumns = "id_tasklist"))
public class TaskSublist {

    @PrimaryKey(autoGenerate = true)
    private long id_tasksublist;

    @ColumnInfo(name = "id_tasklist")
    private long id_tasklist;


    public long getId_tasksublist() {
        return id_tasksublist;
    }

    public void setId_tasksublist(long id_tasksublist) {
        this.id_tasksublist = id_tasksublist;
    }

    public long getId_tasklist() {
        return id_tasklist;
    }

    public void setId_tasklist(long id_tasklist) {
        this.id_tasklist = id_tasklist;
    }
}
