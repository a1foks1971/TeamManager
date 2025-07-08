package com.example.teamman.db.employee;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String position;

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }
}