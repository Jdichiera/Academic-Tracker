package com.example.academictracker.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.academictracker.entity.Term;
import com.example.academictracker.repository.TermRepository;
import com.example.academictracker.view.ViewTermActivity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private TermRepository repository;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(Term term) {
        repository.insert(term);
    }

    public void update(Term term) {
        repository.update(term);
    }

    public void deleteTerm(ViewTermActivity activity, Term term) {
        repository.deleteTerm(activity, term);
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public LiveData<Term> getTerm(int id) {
        return repository.getTerm(id);
    }

    public void getTermCourses(Term term) {
        repository.getTermCourses(term);
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void doesTermHaveCourses(Term term) {
        repository.doesTermHaveCourses(term);
    }
}
