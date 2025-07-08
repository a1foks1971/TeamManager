package com.example.teamman;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teamman.db.AppDatabase;
import com.example.teamman.web.EmbeddedWebServer;

public class MainActivity extends AppCompatActivity {
    private EmbeddedWebServer webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            webServer = new EmbeddedWebServer(8080, db);
        } catch (Exception e) {
            Log.e("WebServer", "Ошибка запуска", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webServer != null) {
            webServer.stop();
        }
    }
}