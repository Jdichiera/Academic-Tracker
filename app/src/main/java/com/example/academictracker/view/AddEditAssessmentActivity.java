package com.example.academictracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.academictracker.R;
import java.util.Calendar;

public class AddEditAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.academictracker.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.academictracker.view.EXTRA_TITLE";
    public static final String EXTRA_COURSE_ID = "com.example.academictracker.view.EXTRA_COURSE_ID";
    public static final String EXTRA_DUE_DATE = "com.example.academictracker.view.EXTRA_DUE_DATE";
    public static final String EXTRA_IS_PERFORMANCE = "com.example.academictracker.view.EXTRA_IS_PERFORMANCE";

    int courseId;
    DatePicker datePickerDueDate;
    EditText editTextTitle;
    RadioButton radioButtonPerformance;
    RadioButton radioButtonObjective;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);
        editTextTitle = findViewById(R.id.assessment_add_title);
        datePickerDueDate = findViewById(R.id.assessment_add_due_date);
        radioButtonObjective = findViewById(R.id.radio_objective);
        radioButtonPerformance = findViewById(R.id.radio_performance);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Assessment");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(intent.getLongExtra(EXTRA_DUE_DATE, 0));
            datePickerDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            if (intent.getBooleanExtra(EXTRA_IS_PERFORMANCE, false)) {
                radioButtonPerformance.toggle();
            } else {
                radioButtonObjective.toggle();
            }

        } else {
            setTitle("Add Assessment");
        }
    }

    public void saveAssessment(View view) {
        long dueDateLong;
        boolean isPerformance = false;
        String title = editTextTitle.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePickerDueDate.getYear(), datePickerDueDate.getMonth(), datePickerDueDate.getDayOfMonth());
        dueDateLong = calendar.getTimeInMillis();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioButtonPerformance.isChecked()) {
            isPerformance = true;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DUE_DATE, dueDateLong);
        data.putExtra(EXTRA_COURSE_ID, courseId);
        data.putExtra(EXTRA_IS_PERFORMANCE, isPerformance);

        int assessmentId = getIntent().getIntExtra(EXTRA_ID, -1);
        if (assessmentId != -1) {
            data.putExtra(EXTRA_ID, assessmentId);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
