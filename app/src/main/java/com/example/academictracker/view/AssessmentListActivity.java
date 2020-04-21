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
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.academictracker.R;
import com.example.academictracker.adapters.AssessmentListAdapter;
import com.example.academictracker.entity.Assessment;
import com.example.academictracker.factories.AssessmentViewModelFactory;
import com.example.academictracker.viewmodel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {
    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        Intent intent = getIntent();
        final int courseId = intent.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);


        FloatingActionButton buttonAddAssessment = findViewById(R.id.button_list_add_assessment);
        buttonAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentListActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, courseId);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.assessment_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final AssessmentListAdapter adapter = new AssessmentListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AssessmentListAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Assessment assessment) {
                editAssessment(assessment);
            }

            @Override
            public void onViewClick(Assessment assessment) {
                viewAssessment(assessment);
            }
        });

        assessmentViewModel = ViewModelProviders.of(this, new AssessmentViewModelFactory(this.getApplication(), courseId)).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessmentsForCourse().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });
    }

    private void editAssessment(Assessment assessment) {
        Intent intent = new Intent(AssessmentListActivity.this, AddEditAssessmentActivity.class);
        intent.putExtra(AddEditAssessmentActivity.EXTRA_ID, assessment.getAssessmentId());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_TITLE, assessment.getAssessmentTitle());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_DUE_DATE, assessment.getAssessmentDueDate());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, assessment.getCourseId());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_IS_PERFORMANCE, assessment.getIsPerformance());
        startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
    }

    private void viewAssessment(Assessment assessment) {
        Intent intent = new Intent(AssessmentListActivity.this, ViewAssessmentActivity.class);
        intent.putExtra(AddEditAssessmentActivity.EXTRA_ID, assessment.getAssessmentId());
        intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, assessment.getCourseId());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title;
        long dueDate;
        int courseId;
        boolean isPerformance;

        if (resultCode == RESULT_OK) {
            title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            dueDate = data.getLongExtra(AddEditAssessmentActivity.EXTRA_DUE_DATE, 0);
            courseId = data.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);
            isPerformance = data.getBooleanExtra(AddEditAssessmentActivity.EXTRA_IS_PERFORMANCE, false);

            Assessment assessment = new Assessment(title, dueDate, isPerformance);
            assessment.setCourseId(courseId);

            if (requestCode == ADD_ASSESSMENT_REQUEST) {
                assessmentViewModel.insert(assessment);
                Toast.makeText(this, "Assessment Added : " + title, Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_ASSESSMENT_REQUEST) {
                int assessmentId = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);

                if (assessmentId == -1) {
                    Toast.makeText(this, "Assessment cannot be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                assessment.setAssessmentId(assessmentId);
                assessment.setCourseId(courseId);
                assessmentViewModel.update(assessment);
                Toast.makeText(this, "Assessment Updated : ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
