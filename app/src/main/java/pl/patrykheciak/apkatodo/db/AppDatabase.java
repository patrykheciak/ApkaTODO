package pl.patrykheciak.apkatodo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Task.class, TaskSublist.class, TaskList.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskListDao taskListDao();
    public abstract TaskSublistDao taskSublistDao();
    public abstract TaskDao taskDao();
}
