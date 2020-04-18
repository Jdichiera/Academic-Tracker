package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.TermViewModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewTermActivity extends AppCompatActivity {
    private Term term;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TermViewModel termViewModel;
    public static final int EDIT_TERM_REQUEST = 1;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        Intent intent = getIntent();
        termId = intent.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

        textViewTitle = findViewById(R.id.term_view_title);
        textViewStartDate = findViewById(R.id.term_view_start_date);
        textViewEndDate = findViewById(R.id.course_view_end_date);
        Button buttonViewCourseList = findViewById(R.id.button_view_course_list);
        Button buttonDeleteTerm = findViewById(R.id.button_view_delete_term);
        Button buttonEditTerm = findViewById(R.id.button_view_edit_term);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getTerm(termId).observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {
                if (term != null) {
                    if (term.getId() > 0) {
                        calendar.setTimeInMillis(term.getStartDate());
                        textViewTitle.setText(term.getTitle());
                        textViewStartDate.setText((dateFormat.format(calendar.getTime())));
                        calendar.setTimeInMillis(term.getEndDate());
                        textViewEndDate.setText((dateFormat.format(calendar.getTime())));
                    }
                }
            }
        });

        buttonViewCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCourseList();
            }
        });

        buttonDeleteTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTerm();
            }
        });

        buttonEditTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTerm();
            }
        });
    }

    private void deleteTerm() {
        term = createTerm();
        term.setId(termId);
        try {
            termViewModel.deleteTerm(this, term);
            finish();
        } catch (SQLiteException ex) {
        }

    }

    private void editTerm() {
        term = createTerm();
        Intent intent = new Intent(ViewTermActivity.this, AddEditTermActivity.class);
        intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
        intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
        intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStartDate());
        intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEndDate());
        startActivityForResult(intent, EDIT_TERM_REQUEST);
    }

    private void viewCourseList() {
        Intent intent = new Intent(ViewTermActivity.this, CourseListActivity.class);
        intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termId);
        startActivity(intent);
    }

    private Term createTerm() {
        long startDateLong;
        long endDateLong;
        String title = textViewTitle.getText().toString();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(textViewStartDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startDateLong = calendar.getTimeInMillis();
        try {
            calendar.setTime(dateFormat.parse(textViewEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDateLong = calendar.getTimeInMillis();

        Term term = new Term(title, startDateLong, endDateLong);
        term.setId(termId);
        return term;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            long startDate = data.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0);
            long endDate = data.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0);
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

            Term term = new Term(title, startDate, endDate);

            if (id == -1) {
                Toast.makeText(this, "Term cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            term.setId(id);
            termViewModel.update(term);
        } else {
            Toast.makeText(this, "Term Update Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
