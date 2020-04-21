package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academictracker.R;

import java.util.Calendar;

public class AddEditTermActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.academictracker.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";
    public static final String EXTRA_START_DATE = "com.example.academictracker.view.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.academictracker.view.EXTRA_END_DATE";


    private EditText editTextTitle;
    private DatePicker startDate;
    private DatePicker endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);
        editTextTitle = findViewById(R.id.term_add_title);
        startDate = findViewById(R.id.term_add_start);
        endDate = findViewById(R.id.term_add_end);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Term");
            Calendar calendar = Calendar.getInstance();
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_START_DATE, 0));
            startDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_END_DATE, 0));
            endDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            setTitle("Add Term");
        }
    }

    public void saveTerm(View view) {
        long startDateLong;
        long endDateLong;
        String title = editTextTitle.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
        startDateLong = calendar.getTimeInMillis();
        calendar.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());
        endDateLong = calendar.getTimeInMillis();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, startDateLong);
        data.putExtra(EXTRA_END_DATE, endDateLong);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
