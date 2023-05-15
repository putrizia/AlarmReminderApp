package com.example.reminderapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insertAll(EntityClass entityClass);

    @Query("SELECT * FROM myTable")
    List<EntityClass> getAllData();

    @Delete
    void delete (EntityClass entityClass);

    @Query("UPDATE myTable SET eventname=:eventname, eventdate=:eventdate, eventtime=:eventtime WHERE id=:id")
    void update (int id, String eventname, String eventdate, String eventtime);

    @Query("SELECT * FROM myTable WHERE id=:id")
    EntityClass get(int id);
}
