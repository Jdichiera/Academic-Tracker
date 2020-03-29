package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academictracker.R;
import com.example.academictracker.fragments.DatePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTermActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";


    private EditText editTextTitle;
    private EditText startDate;
    private EditText endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        editTextTitle = findViewById(R.id.add_term_title);
        startDate = findViewById(R.id.add_term_start);
        endDate = findViewById(R.id.add_term_end);




    }

    public void saveTerm(View view) {
        String title = editTextTitle.getText().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);

        setResult(RESULT_OK, data);
        finish();
    }

//    public void setStartDate(View view) {
//        DialogFragment datePicker = new DatePickerFragment();
//        datePicker.show(getSupportFragmentManager(), "date picker");
//        Log.d("tag", "message");
//    }
//
//    public void setEndDate(View view) {
//        DialogFragment datePicker = new DatePickerFragment();
//        datePicker.show(getSupportFragmentManager(), "date picker");
//    }


//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar selectedCalendar = Calendar.getInstance();
//        selectedCalendar.set(Calendar.YEAR, year);
//        selectedCalendar.set(Calendar.MONTH, month);
//        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        String selectedDate = DateFormat.getDateInstance().format(selectedCalendar.getTime());
//
//
////        EditText startDate = findViewById(R.id.add_term_start);
////        EditText endDate = findViewById(R.id.add_term_end);
//        Toast.makeText(this, String.valueOf(this.startDate.hasFocus()), Toast.LENGTH_SHORT).show();
//        if (this.startDate.hasFocus()) {
//            this.startDate.setText(selectedDate);
//        } else {
//            this.endDate.setText(selectedDate);
//        }
//    }
}
