package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatFactAdapter extends RecyclerView.Adapter<CatFactAdapter.CatFactViewHolder> {
    private List<Fact> catFacts;

    public void setCatFacts(List<Fact> catFacts) {
        this.catFacts = catFacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatFactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fact_item, parent, false);
        return new CatFactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatFactViewHolder holder, int position) {
        holder.bind(catFacts.get(position));
    }

    @Override
    public int getItemCount() {
        return (catFacts == null) ? 0 : catFacts.size();
    }

    static class CatFactViewHolder extends RecyclerView.ViewHolder {
        private TextView factTextView;

        public CatFactViewHolder(@NonNull View itemView) {
            super(itemView);
            factTextView = itemView.findViewById(R.id.factTextView);
        }

        public void bind(Fact Fact) {
            factTextView.setText(Fact.getText());
        }
    }
}
