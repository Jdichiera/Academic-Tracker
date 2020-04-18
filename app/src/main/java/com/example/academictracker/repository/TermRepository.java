package com.example.academictracker.repository;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import com.example.academictracker.dao.TermDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Term;
import com.example.academictracker.view.ViewTermActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

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

    public LiveData<Term> getTerm(int id) {
        return termDao.getTerm(id);
    }

    public void getTermCourses(Term term) {
        new GetTermCoursesAsyncTask(termDao).execute(term);
    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termDao).execute();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void doesTermHaveCourses(Term term) {
        new CheckTermForCoursesAsyncTask(termDao, term).execute();
    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDao.insert(terms[0]);
            return null;
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

    private static class CheckTermForCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private Term term;

        private CheckTermForCoursesAsyncTask(TermDao termDao, Term term) {
            this.termDao = termDao;
            this.term = term;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            term.setTermCourseCount(termDao.getCoursesForTerm(term.getId()));
            return null;
        }
    }

    private static class GetTermCoursesAsyncTask extends AsyncTask<Term, Void, Integer> {
        private TermDao termDao;

        private GetTermCoursesAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Integer doInBackground(Term... terms) {
            terms[0].setTermCourseCount(this.termDao.getCoursesForTerm(terms[0].getId()));
            return null;
        }
    }
}
