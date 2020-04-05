package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.entity.Term;
import com.example.academictracker.viewmodel.TermViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        textViewTitle = findViewById(R.id.term_view_title);
        textViewStartDate = findViewById(R.id.term_view_start_date);
        textViewEndDate = findViewById(R.id.term_view_end_date);
        buttonViewCourseList = findViewById(R.id.button_view_course_list);
        buttonDeleteTerm = findViewById(R.id.button_view_delete_term);
        buttonEditTerm = findViewById(R.id.button_view_edit_term);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        Intent intent = getIntent();
        int id = intent.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
        String termTitle = intent.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
        long startDateInMillis = intent.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0);
        long endDateInMillis = intent.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        calendar.setTimeInMillis(startDateInMillis);
        Date startDate = calendar.getTime();
        calendar.setTimeInMillis(endDateInMillis);
        Date endDate = calendar.getTime();

        textViewTitle.setText(termTitle);
        textViewStartDate.setText(dateFormat.format(startDate));
        textViewEndDate.setText(dateFormat.format(endDate));
        term = new Term(termTitle, startDateInMillis, endDateInMillis);
        term.setId(id);

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
                termViewModel.delete(term);
                finish();
            }
        });

        buttonEditTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewTermActivity.this, "Edit Term", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int id;
        String title;
        long startDate;
        long endDate;

        if (resultCode == RESULT_OK) {
            title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            startDate = data.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0);
            endDate = data.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0);


            Term term = new Term(title, startDate, endDate);
            id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Term cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            term.setId(id);
            if (requestCode == EDIT_TERM_REQUEST) {
                termViewModel.update(term);
            }
        } else {
            Toast.makeText(this, "Action Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
