package com.example.academictracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academictracker.R;
import com.example.academictracker.entity.Course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListHolder> {
    private List<Course> courses = new ArrayList<>();
    CourseListAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public CourseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseListHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListHolder holder, int position) {
        Course courseAtPosition = courses.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date(courseAtPosition.getCourseStartDate());
        Date endDate = new Date(courseAtPosition.getCourseEndDate());

        holder.textViewCourseTitle.setText(courseAtPosition.getCourseTitle());
        holder.textViewStartDate.setText(dateFormat.format(startDate));
        holder.textViewEndDate.setText(dateFormat.format(endDate));
        holder.textViewCourseStatus.setText(courseAtPosition.getCourseStatus());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }


    class CourseListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewCourseTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;
        private TextView textViewCourseStatus;
        private Button buttonEditCourse;
        private Button buttonViewCourse;

        CourseListHolder(View itemView) {
            super(itemView);

            textViewCourseTitle = itemView.findViewById(R.id.course_title);
            textViewStartDate = itemView.findViewById(R.id.course_start_date);
            textViewEndDate = itemView.findViewById(R.id.course_end_date);
            textViewCourseStatus = itemView.findViewById(R.id.course_status);
            buttonEditCourse = itemView.findViewById(R.id.button_course_edit_course);
            buttonViewCourse = itemView.findViewById(R.id.button_course_view_course);
            buttonEditCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(courses.get(position));
                    }
                }
            });
            buttonViewCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onViewClick(courses.get(position));
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        void onEditClick(Course course);
        void onViewClick(Course course);
    }

    public void setOnItemClickListener(CourseListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
