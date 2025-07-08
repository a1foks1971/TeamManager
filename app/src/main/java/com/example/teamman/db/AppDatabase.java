package com.example.teamman.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamman.db.person.PersonConst;
import com.example.teamman.db.person.PersonConstDao;
import com.example.teamman.db.person.PersonDetails;
import com.example.teamman.db.person.PersonDetailsDao;

@Database(
    entities = {
        PersonConst.class,
        PersonDetails.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract PersonConstDao personConstDao();     // бывший employeeDao()
    public abstract PersonDetailsDao personDetailsDao(); // новый DAO

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "platoon_db")
                    .allowMainThreadQueries() // Только для тестов / отладки
                    .build();
        }
        return INSTANCE;
    }
}

