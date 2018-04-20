package pl.patrykheciak.apkatodo;

import android.app.Application;
import android.arch.persistence.room.Room;

import pl.patrykheciak.apkatodo.db.AppDatabase;

public class TodoApplication extends Application {

    private AppDatabase dbInstance;

    public AppDatabase getDbInstance() {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name")
                    .allowMainThreadQueries().build();
        }
        return dbInstance;
    }
}
