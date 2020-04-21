package com.example.academictracker.repository;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.academictracker.dao.TermDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Term;
import com.example.academictracker.view.ViewTermActivity;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;
    public static boolean deleteSuccessful;
    public static long insertedId;

    public TermRepository(Application application) {
        AcademicTrackerDatabase database = AcademicTrackerDatabase.getInstance(application);
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();
    }

    public void insert(Term term) {
       new InsertTermAsyncTask(termDao).execute(term);
    }

    public void update(Term term) {
        new UpdateTermAsyncTask(termDao).execute(term);
    }

    public void deleteTerm(ViewTermActivity activity, Term term) {
        new DeleteTermAsyncTask(activity, termDao).execute(term);
    }

    public void resetKeys() {
        new ResetKeysAsyncTask(termDao).execute();
    }

    public LiveData<Term> getTerm(int id) {
        return termDao.getTerm(id);
    }

    public LiveData<Term> getCurrentTerm() {
        Calendar calendar = Calendar.getInstance();
        return termDao.getCurrentTerm(calendar.getTimeInMillis());
    }

    public LiveData<Integer> getCurrentTermId() {
        Calendar calendar = Calendar.getInstance();
        return termDao.getCurrentTermId(calendar.getTimeInMillis());
    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termDao).execute();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Long> {
        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Long doInBackground(Term... terms) {
            long id = termDao.insert(terms[0]);
            return id;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            insertedId = aLong;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDao termDao;

        private UpdateTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDao.update(terms[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private final WeakReference<ViewTermActivity> vierTermActivityContext;
        private TermDao termDao;
        private boolean deletionSuccessful;


        private DeleteTermAsyncTask(ViewTermActivity actvity, TermDao termDao) {
            this.termDao = termDao;
            this.vierTermActivityContext = new WeakReference<>(actvity);
        }

        @Override
        protected Void doInBackground(Term... terms) {
            try {
                termDao.deleteTerm(terms[0]);
                deletionSuccessful = true;
            } catch (SQLiteException ex) {
                deletionSuccessful = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Activity activity = this.vierTermActivityContext.get();
            if (!deletionSuccessful) {
                Toast.makeText(activity, "Unable to delete terms that have courses associated with them. Please delete all courses for the term and try again.", Toast.LENGTH_SHORT).show();
                deleteSuccessful = false;
            } else {
                deleteSuccessful = true;
            }
        }
    }

    private static class DeleteAllTermsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;

        private DeleteAllTermsAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termDao.deleteAllTerms();
            return null;
        }
    }

    private static class ResetKeysAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;

        private ResetKeysAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termDao.resetKeys();
            return null;
        }
    }
}
