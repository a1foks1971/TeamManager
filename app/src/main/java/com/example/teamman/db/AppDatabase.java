package com.example.teamman.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamman.db.employee.Employee;
import com.example.teamman.db.employee.EmployeeDao;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract EmployeeDao employeeDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "employee_db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}