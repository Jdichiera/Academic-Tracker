package com.example.academictracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academictracker.R;
import com.example.academictracker.adapters.TermListAdapter;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TermListActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_list_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermListActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.term_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final TermListAdapter adapter = new TermListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TermListAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(Term term) {
                viewTerm(term);
            }

            @Override
            public void onEditClick(Term term) {
                editTerm(term);
            }
        });

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
        String title;
        long startDate;
        long endDate;

        if (resultCode == RESULT_OK) {
            title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            startDate = data.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0);
            endDate = data.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0);
            Term term = new Term(title, startDate, endDate);

            if (requestCode == ADD_TERM_REQUEST) {
                termViewModel.insert(term);
                Toast.makeText(this, "Term Added : " + title, Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_TERM_REQUEST) {
                int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

                if (id == -1) {
                    Toast.makeText(this, "Term cannot be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                term.setId(id);
                termViewModel.update(term);
                Toast.makeText(this, "Term Updated : " + title, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void editTerm(Term term) {
        Intent intent = new Intent(TermListActivity.this, AddEditTermActivity.class);
        intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
        intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
        intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStartDate());
        intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEndDate());
        startActivityForResult(intent, EDIT_TERM_REQUEST);
    }

    private void viewTerm(Term term) {
        Intent intent = new Intent(TermListActivity.this, ViewTermActivity.class);
        intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
        startActivity(intent);
    }
}
