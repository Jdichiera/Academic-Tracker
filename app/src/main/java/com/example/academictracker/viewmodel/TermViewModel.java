package com.example.academictracker.viewmodel;

import android.app.Application;
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
    public boolean deleteSuccessful;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }

    public long insert(Term term) {
        repository.insert(term);
        return repository.insertedId;
    }

    public void update(Term term) {
        repository.update(term);
    }

    public void deleteTerm(ViewTermActivity activity, Term term) {
        repository.deleteTerm(activity, term);
        deleteSuccessful = repository.deleteSuccessful;
    }

    public void resetKeys() {
        repository.resetKeys();
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public LiveData<Term> getTerm(int id) {
        return repository.getTerm(id);
    }

    public LiveData<Term> getCurrentTerm() {
        return repository.getCurrentTerm();
    }

    public LiveData<Integer> getCurrentTermId() {
        return repository.getCurrentTermId();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public int getTermListSize() {
        return allTerms.getValue().size();
    }
}
