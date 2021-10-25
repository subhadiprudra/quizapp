package com.lunarday.myquiz.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lunarday.myquiz.R;

public class HomeActivity extends AppCompatActivity {

    CardView music,computer,nature,books,sports,history;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        music = findViewById(R.id.music);
        computer = findViewById(R.id.computer);
        nature = findViewById(R.id.nature);
        books = findViewById(R.id.book);
        sports = findViewById(R.id.sports);
        history = findViewById(R.id.history);

        intent = new Intent(this,QuestionsActivity.class);

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",12);
                startActivity(intent);
            }
        });

        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",18);
                startActivity(intent);
            }
        });

        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",17);
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",10);
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",21);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("c",23);
                startActivity(intent);
            }
        });

    }
}