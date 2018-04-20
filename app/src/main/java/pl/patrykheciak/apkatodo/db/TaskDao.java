package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getAll();

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<Task> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    Task findByName(String first, String last);

    @Query("SELECT * FROM Task WHERE id_task IS(:id)")
    Task findById(int id);

    @Query("SELECT * FROM Task WHERE id_tasksublist IS (:id_tasksublist)")
    List<Task> tasksOfTasksubist(long id_tasksublist);

    @Insert
    void insertAll(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Task task);

    @Delete
    void delete(Task task);
}