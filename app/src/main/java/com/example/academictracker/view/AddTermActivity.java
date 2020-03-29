package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academictracker.R;

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
}
