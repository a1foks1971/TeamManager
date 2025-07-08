package com.example.teamman.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamman.R;
import com.example.teamman.db.person.PersonConst;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<PersonConst> people;

    public PersonAdapter(List<PersonConst> people) {
        this.people = people;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView positionText;

        public ViewHolder(View view) {
            super(view);
            nameText = view.findViewById(R.id.textName);
            positionText = view.findViewById(R.id.textPosition);
        }
    }

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonConst emp = people.get(position);
        holder.nameText.setText(emp.lastName);
        holder.positionText.setText(emp.firstName);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void updateList(List<PersonConst> newList) {
        people = newList;
        notifyDataSetChanged();
    }
}