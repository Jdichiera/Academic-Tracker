package com.example.academictracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.academictracker.dao.TermDao;
import com.example.academictracker.database.AcademicTrackerDatabase;
import com.example.academictracker.entity.Term;

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

    public void delete(Term term) {
        new DeleteTermAsyncTask(termDao).execute(term);

    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termDao).execute();
    }

    public LiveData<List<Term>> getAllNotes() {
        return allTerms;
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
        private TermDao termDao;

        private DeleteTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDao.delete(terms[0]);
            return null;
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

}
