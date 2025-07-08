package com.example.teamman.db.employee;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM Employee")
    List<Employee> getAll();

    @Insert
    void insert(Employee employee);
}