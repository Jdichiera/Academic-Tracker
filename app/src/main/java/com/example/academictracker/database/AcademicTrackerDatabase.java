package com.example.academictracker.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.academictracker.dao.AssessmentDao;
import com.example.academictracker.dao.CourseDao;
import com.example.academictracker.dao.CourseNoteDao;
import com.example.academictracker.dao.TermDao;
import com.example.academictracker.entity.Assessment;
import com.example.academictracker.entity.Course;
import com.example.academictracker.entity.CourseNote;
import com.example.academictracker.entity.Term;

@Database(entities = {Term.class, Course.class, CourseNote.class, Assessment.class},
        version = 8, exportSchema = false)
public abstract class AcademicTrackerDatabase extends RoomDatabase {
    private static AcademicTrackerDatabase instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract CourseNoteDao courseNoteDao();
    public abstract AssessmentDao assessmentDao();

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
//            new PopulateDBAsyncTask(instance).execute();
        }
    };

//    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
//        private TermDao termDao;
//
//        private PopulateDBAsyncTask(AcademicTrackerDatabase db) {
//            termDao = db.termDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
////            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
////            termDao.insert(
////                    new Term(
////                            "Term 1 Title",
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli(),
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli()
////                    )
////            );
////            termDao.insert(
////                    new Term(
////                            "Term 2 Title",
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli(),
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli()
////                    )
////            );
////            termDao.insert(
////                    new Term(
////                            "Term 3 Title",
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli(),
////                            LocalDate.parse("01-01-2020", dateFormatter)
////                                    .atStartOfDay(ZoneOffset.UTC)
////                                    .toInstant()
////                                    .toEpochMilli()
////                    )
////            );
//            return null;
//        }
//    }
}
