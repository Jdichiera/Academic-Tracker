package com.example.academictracker.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.adapters.TermAdapter;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermListActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermListActivity.this, AddTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTermActivity.EXTRA_TITLE);

            Term term = new Term(title, 1585502595L, 1585502595L);
            termViewModel.insert(term);

            Toast.makeText(this, "Term Saved : " + title, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
