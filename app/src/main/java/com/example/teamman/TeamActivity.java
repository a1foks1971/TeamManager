package com.example.teamman;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamActivity extends AppCompatActivity {
    private EditText editName, editPosition;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private AppDatabase db;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        editName = findViewById(R.id.editName);
        editPosition = findViewById(R.id.editPosition);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);

        db = AppDatabase.getInstance(this);

        adapter = new EmployeeAdapter(db.employeeDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String position = editPosition.getText().toString().trim();
                if (!name.isEmpty() && !position.isEmpty()) {
                    db.employeeDao().insert(new Employee(name, position));
                    adapter.updateList(db.employeeDao().getAll());
                    editName.setText("");
                    editPosition.setText("");
                }
            }
        });
    }
}