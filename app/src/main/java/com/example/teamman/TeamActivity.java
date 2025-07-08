package com.example.teamman;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamman.db.AppDatabase;
import com.example.teamman.db.person.PersonConst;
import com.example.teamman.ui.common.adapters.PersonAdapter;

public class TeamActivity extends AppCompatActivity {
    private EditText editName, editPosition;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
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

        adapter = new PersonAdapter(db.personConstDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String position = editPosition.getText().toString().trim();
                if (!name.isEmpty() && !position.isEmpty()) {
                    db.personConstDao().insert(
                        new PersonConst.Builder()
                        .lastName(name)
                        .firstName(position)
                        .build()
                    );
                    adapter.updateList(db.personConstDao().getAll());
                    editName.setText("");
                    editPosition.setText("");
                }
            }
        });
    }
}