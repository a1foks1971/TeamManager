package com.example.teamman.db.person;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "PersonDetails",
        foreignKeys = @ForeignKey(
                entity = PersonConst.class,
                parentColumns = "id",
                childColumns = "personId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("personId")}
)
public class PersonDetails {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int personId;      // внешний ключ на PersonConst.id
    public String callsign;   // позывной

    public String bloodType;
    public String militaryRank;
    public String notes;

    public PersonDetails() {}
}

