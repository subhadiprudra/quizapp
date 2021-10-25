package com.lunarday.myquiz.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lunarday.myquiz.R;

public class Result extends AppCompatActivity {

    TextView scoreTv,totalTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreTv = findViewById(R.id.score);
        totalTv = findViewById(R.id.total);

        scoreTv.setText(getIntent().getIntExtra("score",0)+"");
        totalTv.setText("/"+getIntent().getIntExtra("total",0));

    }
}