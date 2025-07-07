package com.example.teamman;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // подключаем layout с кнопкой

        Button buttonTeam = findViewById(R.id.buttonTeam);
        buttonTeam.setOnClickListener(v -> {
            // Переход на экран "Команда"
            Intent intent = new Intent(MainActivity.this, TeamActivity.class);
            startActivity(intent);
        });
    }
}