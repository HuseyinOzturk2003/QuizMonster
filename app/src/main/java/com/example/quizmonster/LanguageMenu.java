package com.example.quizmonster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LanguageMenu extends AppCompact {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_menu);
        ImageButton en = findViewById(R.id.btn_en);
        ImageButton de = findViewById(R.id.btn_de);
        LanguageManager lang = new LanguageManager(this);
        en.setOnClickListener(view ->{
            lang.updateResource("eng");
            recreate();
        });
        de .setOnClickListener(view ->{
            lang.updateResource("de");
            recreate();
        });

        findViewById(R.id.btn_go).setOnClickListener(view ->{
            Intent intent = new Intent(LanguageMenu.this, MainActivity.class);
            startActivity(intent);
        });

    }
}