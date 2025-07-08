package com.example.teamman.db.employee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamman.R;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employees;

    public EmployeeAdapter(List<Employee> employees) {
        this.employees = employees;
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
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Employee emp = employees.get(position);
        holder.nameText.setText(emp.name);
        holder.positionText.setText(emp.position);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void updateList(List<Employee> newList) {
        employees = newList;
        notifyDataSetChanged();
    }
}