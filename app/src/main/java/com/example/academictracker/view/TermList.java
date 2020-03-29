package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.adapters.TermAdapter;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.TermViewModel;

import java.util.List;

public class TermList extends AppCompatActivity {
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        RecyclerView recyclerView = findViewById(R.id.term_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();

        recyclerView.setAdapter(adapter);

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });
    }
}
