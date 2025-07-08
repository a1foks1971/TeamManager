package com.example.teamman.db.person;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonConstDao {
    @Query("SELECT * FROM PersonConst")
    List<PersonConst> getAll();

    @Insert
    void insert(PersonConst personConst);
}