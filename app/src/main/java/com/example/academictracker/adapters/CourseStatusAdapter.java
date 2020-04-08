package com.example.academictracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.academictracker.R;

import java.util.ArrayList;

public class CourseStatusAdapter extends ArrayAdapter<CourseStatusItem> {

    public CourseStatusAdapter(Context context, ArrayList<CourseStatusItem> courseStatusList) {
        super(context, 0, courseStatusList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_status_spinner_row, parent, false);
        }
        TextView textViewCourseStatusName = convertView.findViewById(R.id.course_status_spinner_text);


        CourseStatusItem currentCourseStatus = getItem(position);
        if (currentCourseStatus != null) {
            textViewCourseStatusName.setText(currentCourseStatus.getCourseStatusName());
        }

        return convertView;
    }
}
