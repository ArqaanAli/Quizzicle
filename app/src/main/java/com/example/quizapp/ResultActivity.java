package com.example.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView t1;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        t1 = findViewById(R.id.score);
        ok = findViewById(R.id.ok_button);

        /*inal Intent i = getIntent();
        String questions = i.getStringExtra("total");
        String correct = i.getStringExtra("correct");

        t1.setText("Your Score : "+correct+ "/" +questions);*/


    }
}
