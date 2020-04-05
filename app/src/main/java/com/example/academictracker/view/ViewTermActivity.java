package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academictracker.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewTermActivity extends AppCompatActivity {
    private TextView title;
    private TextView startDate;
    private TextView endDate;
    private Button buttonViewCourseList;
    private Button buttonDeleteTerm;
    private Button buttonEditTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        title = findViewById(R.id.term_view_title);
        startDate = findViewById(R.id.term_view_start_date);
        endDate = findViewById(R.id.term_view_end_date);
        buttonViewCourseList = findViewById(R.id.button_view_course_list);
        buttonDeleteTerm = findViewById(R.id.button_view_delete_term);
        buttonEditTerm = findViewById(R.id.button_view_edit_term);

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
            }
        });

        buttonEditTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewTermActivity.this, "Edit Term", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        title.setText(intent.getStringExtra(AddEditTermActivity.EXTRA_TITLE));
        calendar.setTimeInMillis(intent.getLongExtra(AddEditTermActivity.EXTRA_START_DATE, 0));
        startDate.setText(dateFormat.format(calendar.getTime()));
        calendar.setTimeInMillis(intent.getLongExtra(AddEditTermActivity.EXTRA_END_DATE, 0));
        endDate.setText(dateFormat.format(calendar.getTime()));


    }
}
