package com.example.academictracker.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.academictracker.dao.TermDao;
import com.example.academictracker.entity.Term;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Database(entities = {Term.class}, version = 1)
public abstract class AcademicTrackerDatabase extends RoomDatabase {
    private static AcademicTrackerDatabase instance;

    public abstract TermDao termDao();

    public static synchronized AcademicTrackerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AcademicTrackerDatabase.class, "terms_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    }

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;

        private PopulateDBAsyncTask(AcademicTrackerDatabase db) {
            termDao = db.termDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termDao.insert(
                    new Term(
                            "Term 1 Title",
                            new GregorianCalendar(2020, 1, 1).getTime(),
                            new GregorianCalendar(2020, 2, 1).getTime()
                    )
            );
            termDao.insert(
                    new Term(
                            "Term 2 Title",
                            new GregorianCalendar(2020, 3, 1).getTime(),
                            new GregorianCalendar(2020, 4, 1).getTime()
                    )
            );
            termDao.insert(
                    new Term(
                            "Term 1 Title",
                            new GregorianCalendar(2020, 5, 1).getTime(),
                            new GregorianCalendar(2020, 6, 1).getTime()
                    )
            );
            return null;
        }
    }
}
