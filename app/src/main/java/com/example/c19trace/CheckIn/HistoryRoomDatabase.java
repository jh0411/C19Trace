//package com.example.c19trace.CheckIn;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Database(entities = {HistoryClass.class}, version = 1, exportSchema = false)
//public abstract class HistoryRoomDatabase extends RoomDatabase {
//
//    private static HistoryRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    public static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//    public abstract HistoryDAO historyDao();
//
//    // Singleton design pattern
//    public static HistoryRoomDatabase getInstance(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (HistoryRoomDatabase.class) {
//                if (INSTANCE == null) {
//                    //instantiate and the SQLite database engine running
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            HistoryRoomDatabase.class, "history_db")
//                            .addCallback(sRoomDatabaseCallback)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            databaseWriteExecutor.execute(() -> {
//                HistoryDAO dao = INSTANCE.historyDao();
//                dao.deleteAll();
//
//                HistoryClass history = new HistoryClass("The Suburban Food Sdn Bhd", "23/05/2022");
//                dao.insert(history);
//            });
//        }
//    };
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//}
