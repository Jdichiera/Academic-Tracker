package com.example.academictracker.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academictracker.R;
import com.example.academictracker.entity.Term;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<Term> terms = new ArrayList<>();
//    private ClickHandler clickHandler;
    OnItemClickListener listener;

//    public TermAdapter(View.OnClickListener listener) {
//        this.listener = listener;
//    }

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
        holder.termViewStartDate.setText(dateFormat.format(startDate));
        holder.termViewEndDate.setText(dateFormat.format(endDate));
//        holder.buttonEditTerm.setOnClickListener(this.listener);
//        holder.buttonEditTerm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "From inside the adapter", Toast.LENGTH_SHORT).show();
//            }
//        });
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

    public Term getTermAt(int position) {
        return terms.get(position);
    }

    class TermHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView termViewTitle;
        private TextView termViewStartDate;
        private TextView termViewEndDate;
        private Button buttonEditTerm;
//        private WeakReference<ClickHandler> listenerRef;

        public TermHolder(@NonNull View itemView) {
            super(itemView);

//            listenerRef = new WeakReference<ClickHandler>(clickHandler);

            termViewTitle = itemView.findViewById(R.id.term_title);
            termViewStartDate = itemView.findViewById(R.id.term_start_date);
            termViewEndDate = itemView.findViewById(R.id.term_end_date);
            buttonEditTerm = itemView.findViewById(R.id.button_edit_term);
            buttonEditTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(terms.get(position));
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

//        @Override
//        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "Clicked in adapter : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            listenerRef.get().onItemClick(getTermAt(getAdapterPosition()));
//
//        }
    }


    public interface OnItemClickListener {
        void onItemClick(Term term);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.clickHandler = clickHandler;
        this.listener = listener;
    }
}
