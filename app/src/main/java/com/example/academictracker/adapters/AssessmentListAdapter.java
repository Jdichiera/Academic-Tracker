package com.example.academictracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academictracker.R;
import com.example.academictracker.entity.Assessment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentListHolder> {
    AssessmentListAdapter.OnItemClickListener listener;
    private List<Assessment> assessments = new ArrayList<>();

    @NonNull
    @Override
    public AssessmentListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessmentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentListHolder(assessmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListHolder holder, int position) {
        String assessmentType = "Objective";
        Assessment assessmentAtPosition = assessments.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date assessmentDueDate = new Date(assessmentAtPosition.getAssessmentDueDate());
        if (assessmentAtPosition.getIsPerformance() == true) {
            assessmentType = "Performance";
        }

        holder.textViewAssessmentTitle.setText(assessmentAtPosition.getAssessmentTitle());
        holder.textViewAssessmentDueDate.setText(dateFormat.format(assessmentDueDate));
        holder.textViewAssessmentType.setText(assessmentType);
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }


    class AssessmentListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewAssessmentTitle;
        private TextView textViewAssessmentDueDate;
        private TextView textViewAssessmentType;

        AssessmentListHolder(View itemView) {
            super(itemView);

            textViewAssessmentTitle = itemView.findViewById(R.id.assessment_title);
            textViewAssessmentDueDate = itemView.findViewById(R.id.assessment_due_date);
            textViewAssessmentType = itemView.findViewById(R.id.assessment_type);
            Button buttonViewAssessment = itemView.findViewById(R.id.button_assessment_view_assessment);
            Button buttonEditAssessment = itemView.findViewById(R.id.button_assessment_edit_assessment);

            buttonEditAssessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(assessments.get(position));
                    }
                }
            });
            buttonViewAssessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onViewClick(assessments.get(position));
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
        }
    }

    public interface OnItemClickListener {
        void onEditClick(Assessment assessment);
        void onViewClick(Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
