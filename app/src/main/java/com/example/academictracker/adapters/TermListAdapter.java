package com.example.academictracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academictracker.R;
import com.example.academictracker.entity.Term;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermListHolder> {
    private List<Term> terms = new ArrayList<>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public TermListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View termView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermListHolder(termView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermListHolder holder, int position) {
        Term termAtPosition = terms.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date(termAtPosition.getStartDate());
        Date endDate = new Date(termAtPosition.getEndDate());

        holder.termViewTitle.setText(termAtPosition.getTitle());
        holder.termViewStartDate.setText(dateFormat.format(startDate));
        holder.termViewEndDate.setText(dateFormat.format(endDate));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public Term getTermAt(int position) {
        return terms.get(position);
    }

    class TermListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView termViewTitle;
        private TextView termViewStartDate;
        private TextView termViewEndDate;
        private Button buttonEditTerm;
        private Button buttonViewTerm;

        public TermListHolder(@NonNull View itemView) {
            super(itemView);

            termViewTitle = itemView.findViewById(R.id.term_title);
            termViewStartDate = itemView.findViewById(R.id.term_start_date);
            termViewEndDate = itemView.findViewById(R.id.term_end_date);
            buttonViewTerm = itemView.findViewById(R.id.button_term_view_term);
            buttonEditTerm = itemView.findViewById(R.id.button_term_edit_term);
            buttonEditTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(terms.get(position));
                    }
                }
            });
            buttonViewTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onViewClick(terms.get(position));
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        void onEditClick(Term term);
        void onViewClick(Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
