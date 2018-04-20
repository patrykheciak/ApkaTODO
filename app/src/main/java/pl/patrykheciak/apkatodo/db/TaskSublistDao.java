package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskSublistDao {

    @Query("SELECT * FROM TaskSublist")
    List<TaskSublist> getAll();

    @Query("SELECT * FROM TaskSublist WHERE id_tasksublist IS(:id)")
    TaskSublist findById(int id);

    @Query("SELECT * FROM TaskSublist WHERE id_tasklist IS (:id_tasklist)")
    List<TaskSublist> tasksublistsOfTasklist(long id_tasklist);

    @Insert
    List<Long> insertAll(TaskSublist... taskSublists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TaskSublist taskSublist);

    @Delete
    void delete(TaskSublist taskSublist);
}
