package com.example.teamman.db.person;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PersonDetailsDao {
    @Insert
    void insert(PersonDetails details);

    @Query("SELECT * FROM PersonDetails WHERE personId = :personId")
    PersonDetails getByPersonId(int personId);
}


