//package com.example.c19trace.CheckIn;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.c19trace.CheckIn.HistoryClass;
//
//import java.util.List;
//
//public class MainViewModel extends AndroidViewModel {
//    private HistoryRepository repository;
//    private LiveData<List<HistoryClass>> allHistory;
//
//    public MainViewModel(@NonNull Application application) {
//        super(application);
//        repository = new HistoryRepository(application);
//        allHistory = repository.getAllHistory();
//    }
//
//    public void insert(HistoryClass history) {
//        repository.insert(history);
//    }
//
//    // pass the allHistory list to the activity that invokes it
//    public LiveData<List<HistoryClass>> getAllHistory() {
//        return allHistory;
//    }
//
//}
