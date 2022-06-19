package com.example.c19trace.CheckIn;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryRepository {
    private HistoryDAO historyDAO;
    private LiveData<List<HistoryClass>> allHistory;
    private HistoryRoomDatabase db;

    public HistoryRepository(Application application){
        db = HistoryRoomDatabase.getInstance(application);
        historyDAO = db.historyDao();
        allHistory = historyDAO.getAllHistory();
    }

    LiveData<List<HistoryClass>> getAllHistory() {return  allHistory;}

    void insert(HistoryClass history){
        HistoryRoomDatabase.databaseWriteExecutor.execute(() -> {
            historyDAO.insert(history);
        });
    }
}
