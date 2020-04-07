package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

public class ViewTermActivity extends AppCompatActivity {
    private Term term;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private Button buttonViewCourseList;
    private Button buttonDeleteTerm;
    private Button buttonEditTerm;
    private TermViewModel termViewModel;
    public static final int EDIT_TERM_REQUEST = 1;
    public static final int DELETE_TERM_REQUEST = 2;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    int id;
    String termTitle;
    long startDateInMillis;
    long endDateInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        Intent intent = getIntent();
        id = intent.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
//        termTitle = intent.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
//        startDateInMillis = intent.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0);
//        endDateInMillis = intent.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0);


//        calendar.setTimeInMillis(startDateInMillis);
//        final Date startDate = calendar.getTime();
//        calendar.setTimeInMillis(endDateInMillis);
//        Date endDate = calendar.getTime();

        textViewTitle = findViewById(R.id.term_view_title);
        textViewStartDate = findViewById(R.id.term_view_start_date);
        textViewEndDate = findViewById(R.id.term_view_end_date);
        buttonViewCourseList = findViewById(R.id.button_view_course_list);
        buttonDeleteTerm = findViewById(R.id.button_view_delete_term);
        buttonEditTerm = findViewById(R.id.button_view_edit_term);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getTerm(id).observe(this, new Observer<Term>() {
            @Override
            public void onChanged(Term term) {
                if (term != null) {
                    calendar.setTimeInMillis(term.getStartDate());
                    textViewTitle.setText(term.getTitle());
                    textViewStartDate.setText((dateFormat.format(calendar.getTime())));
//                Log.e("start", textViewStartDate.getText().toString());
//                Log.e("cal", dateFormat.format(calendar.getTime()));
                    calendar.setTimeInMillis(term.getEndDate());
                    textViewEndDate.setText((dateFormat.format(calendar.getTime())));
//                Log.e("end", textViewEndDate.getText().toString());
//                Log.e("cal", dateFormat.format(calendar.getTime()));
                }
            }
        });

//        textViewTitle.setText(termTitle);
//        textViewStartDate.setText(dateFormat.format(startDate));
//        textViewEndDate.setText(dateFormat.format(endDate));

//        term = new Term(termTitle, startDateInMillis, endDateInMillis);
//        term.setId(id);




        buttonViewCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewTermActivity.this, "View Courses", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewTermActivity.this, "Delete Term", Toast.LENGTH_SHORT).show();
                term = createTerm();
                term.setId(id);



//                long startDateLong;
//                long endDateLong;
//                String title = textViewTitle.getText().toString();
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(text.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
//                startDateLong = calendar.getTimeInMillis();
//                calendar.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());
//                endDateLong = calendar.getTimeInMillis();
//
//                if (title.trim().isEmpty()) {
//                    Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Intent data = new Intent();
//                data.putExtra(EXTRA_TITLE, title);
//                data.putExtra(EXTRA_START_DATE, startDateLong);
//                data.putExtra(EXTRA_END_DATE, endDateLong);
//
//                // If we are in an update scenario - pass the ID so we know that we are updating a term
//                // We pass a -1 default in
//                int id = getIntent().getIntExtra(EXTRA_ID, -1);
//                if (id != -1) {
//                    data.putExtra(EXTRA_ID, id);
//                }
//
//                setResult(RESULT_OK, data);
//                finish();
//
//
//
//
                termViewModel.deleteTerm(term);
                finish();
            }
        });

        buttonEditTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                term = createTerm();

                Log.e("ID", String.valueOf(term.getId()));
                Log.e("TITLE", term.getTitle());
                Log.e("START", String.valueOf(term.getStartDate()));
                Log.e("END", String.valueOf(term.getEndDate()));

//                term = new Term(title, startDateLong, endDateLong);
//                term.setId(id);
                Intent intent = new Intent(ViewTermActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStartDate());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEndDate());
                startActivityForResult(intent, EDIT_TERM_REQUEST);
            }
        });
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
        term.setId(id);

        return term;
    }
}
