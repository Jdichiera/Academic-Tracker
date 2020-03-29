package com.example.academictracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academictracker.R;
import com.example.academictracker.entity.Term;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<Term> terms = new ArrayList<>();

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View termView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(termView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term termAtPosition = terms.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date(termAtPosition.getStartDate());
        Date endDate = new Date(termAtPosition.getEndDate());

        holder.termViewTitle.setText(termAtPosition.getTitle());
        holder.termViewStartDate.setText("starts: " + dateFormat.format(startDate));
        holder.termViewEndDate.setText("ends: " + dateFormat.format(endDate));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<Term> terms) {
        Log.i("Terms", String.valueOf(terms.size()));
        this.terms = terms;
        notifyDataSetChanged();
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView termViewTitle;
        private TextView termViewStartDate;
        private TextView termViewEndDate;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            termViewTitle = itemView.findViewById(R.id.term_title);
            termViewStartDate = itemView.findViewById(R.id.term_start_date);
            termViewEndDate = itemView.findViewById(R.id.term_end_date);
        }
    }
}
