package com.example.c19trace.CheckIn;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Query("SELECT * FROM history_table ORDER BY id DESC")
    LiveData<List<HistoryClass>> getAllHistory();

    @Query("DELETE FROM history_table")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(HistoryClass history);
}
