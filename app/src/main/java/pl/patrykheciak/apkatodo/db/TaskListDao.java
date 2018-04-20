package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskListDao {

    @Query("SELECT * FROM TaskList")
    List<TaskList> getAll();

    @Query("SELECT * FROM TaskList WHERE id_tasklist IS(:id)")
    TaskList findById(int id);

    @Insert
    List<Long> insertAll(TaskList... taskLists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TaskList list);

    @Delete
    void delete(TaskList taskList);
}
