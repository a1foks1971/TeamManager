
package com.example.teamman;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teamman.db.AppDatabase;
import com.example.teamman.web.EmbeddedWebServer;

import java.io.IOException;

public class MainActivity extends Activity {

    private EmbeddedWebServer webServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Создай layout ниже

        // Запускаем сервер на порту 8080
        try {
            webServer = new EmbeddedWebServer(8080, AppDatabase.getInstance(this));
        } catch (IOException e) {
            e.printStackTrace();
            // можно показать ошибку пользователю
        }

        Button openBrowserBtn = findViewById(R.id.open_browser_btn);
        Button closeAppBtn = findViewById(R.id.close_app_btn);

        openBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открыть Chrome с локальным URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://127.0.0.1:8080"));
                intent.setPackage("com.android.chrome"); // чтобы открыть именно Chrome
                // Проверим, есть ли Chrome
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Если Chrome нет, открываем в любом браузере
                    Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://127.0.0.1:8080"));
                    startActivity(fallbackIntent);
                }
            }
        });

        closeAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Останавливаем сервер
                if (webServer != null) {
                    webServer.stop();
                }
                // Закрываем активити, а с ним и приложение
                finishAffinity();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webServer != null) {
            webServer.stop();
        }
    }
}

