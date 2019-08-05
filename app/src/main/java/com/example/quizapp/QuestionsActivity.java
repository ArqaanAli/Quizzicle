package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionsActivity extends AppCompatActivity {

    private TextView ques,timer,count;
    private Button op1,op2,op3,op4;
    int total = 0;
    int correct = 0;
    int wrong = 0;
    DatabaseReference reference;
    private ProgressDialog mquesProgressDialog;

    //FragmentTransaction t;
    ResultDialog m4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ques = findViewById(R.id.questions_text);
        timer = findViewById(R.id.timer_text);
        count = findViewById(R.id.count_text);
        op1 = findViewById(R.id.option_1_button);
        op2 = findViewById(R.id.option_2_button);
        op3 = findViewById(R.id.option_3_button);
        op4 = findViewById(R.id.option_4_button);

        mquesProgressDialog = new ProgressDialog(this);

      /*  FragmentManager manager = getSupportFragmentManager();
         t = manager.beginTransaction();*/
         m4 = new ResultDialog();

        updateQuestions();
    }

    private void updateQuestions() {
        mquesProgressDialog.setTitle("Loading Questions");
        mquesProgressDialog.setMessage("Please wait while we load questions");
        mquesProgressDialog.setCanceledOnTouchOutside(false);
        mquesProgressDialog.show();
        total++;
        if(total > 10){
            Bundle b1 = new Bundle();
            b1.putString("total",String.valueOf(total));
            b1.putString("correct",String.valueOf(correct));
            m4.setArguments(b1);
            //t.commit();
            m4.show(getSupportFragmentManager(),"ResultDialog");
        }
        else{
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mquesProgressDialog.dismiss();

                        reverseTimer(5, timer);

                    final Question questionAnswer = dataSnapshot.getValue(Question.class);

                    ques.setText(questionAnswer.getQuestion());
                    op1.setText(questionAnswer.getOption1());
                    op2.setText(questionAnswer.getOption2());
                    op3.setText(questionAnswer.getOption3());
                    op4.setText(questionAnswer.getOption4());


                    op1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(op1.getText().toString().equals(questionAnswer.getAnswer())){

                                reverseTimer(0,timer);
                                Toast.makeText(QuestionsActivity.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                op1.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();
                                    }

                                },500);
                            }
                            else{
                                Toast.makeText(QuestionsActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong = wrong + 1;
                                op1.setBackgroundColor(Color.RED);

                                if(op2.getText().toString().equals(questionAnswer.getAnswer())){
                                    op2.setBackgroundColor(Color.GREEN);
                                }
                                else if(op3.getText().toString().equals(questionAnswer.getAnswer())){
                                    op3.setBackgroundColor(Color.GREEN);
                                }
                                else if(op4.getText().toString().equals(questionAnswer.getAnswer())){
                                    op4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateQuestions();

                                    }
                                },1500);
                            }
                            count.setText(total+"/10");
                        }
                    });

                    op2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(op2.getText().toString().equals(questionAnswer.getAnswer())){

                                Toast.makeText(QuestionsActivity.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                op2.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();
                                    }
                                },1500);
                            }
                            else{
                                Toast.makeText(QuestionsActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong = wrong + 1;
                                op2.setBackgroundColor(Color.RED);

                                if(op1.getText().toString().equals(questionAnswer.getAnswer())){
                                    op1.setBackgroundColor(Color.GREEN);
                                }
                                else if(op3.getText().toString().equals(questionAnswer.getAnswer())){
                                    op3.setBackgroundColor(Color.GREEN);
                                }
                                else if(op4.getText().toString().equals(questionAnswer.getAnswer())){
                                    op4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();

                                    }
                                },1500);
                            }
                            count.setText(total+"/10");
                        }
                    });

                    op3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(op3.getText().toString().equals(questionAnswer.getAnswer())){

                                Toast.makeText(QuestionsActivity.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                op3.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();
                                    }
                                },1500);
                            }
                            else{
                                Toast.makeText(QuestionsActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong = wrong + 1;
                                op3.setBackgroundColor(Color.RED);

                                if(op1.getText().toString().equals(questionAnswer.getAnswer())){
                                    op1.setBackgroundColor(Color.GREEN);
                                }
                                else if(op2.getText().toString().equals(questionAnswer.getAnswer())){
                                    op2.setBackgroundColor(Color.GREEN);
                                }
                                else if(op4.getText().toString().equals(questionAnswer.getAnswer())){
                                    op4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();

                                    }
                                },1500);
                            }
                            count.setText(total+"/10");
                        }
                    });

                    op4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(op4.getText().toString().equals(questionAnswer.getAnswer())){

                                Toast.makeText(QuestionsActivity.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                op4.setBackgroundColor(Color.GREEN);
                                correct = correct + 1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        updateQuestions();
                                    }
                                },1500);
                            }
                            else{
                                Toast.makeText(QuestionsActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong = wrong + 1;
                                op4.setBackgroundColor(Color.RED);

                                if(op1.getText().toString().equals(questionAnswer.getAnswer())){
                                    op1.setBackgroundColor(Color.GREEN);
                                }
                                else if(op2.getText().toString().equals(questionAnswer.getAnswer())){
                                    op2.setBackgroundColor(Color.GREEN);
                                }
                                else if(op3.getText().toString().equals(questionAnswer.getAnswer())){
                                    op3.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestions();

                                    }
                                },1500);
                            }
                            count.setText(total+"/10");
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    public void reverseTimer(int seconds, final TextView textView){
       new CountDownTimer(seconds * 1000 + 1000,1000){

           public void onTick(long millisUntilFinished){
               int seconds = (int) (millisUntilFinished/1000);
               seconds = seconds % 60 ;
               textView.setText(String.format(String.format("%2d",seconds)));
           }

           public void onFinish(){
               if(total > 10){
               /*Intent intent = new Intent(QuestionsActivity.this,ResultActivity.class);
               intent.putExtra("total",String.valueOf(total));
               intent.putExtra("correct",String.valueOf(correct));
               startActivity(intent);*/
               Bundle b1 = new Bundle();
               b1.putString("total",String.valueOf(total));
               b1.putString("correct",String.valueOf(correct));
               m4.setArguments(b1);
               //t.commit();
               m4.show(getSupportFragmentManager(),"ResultDialog");
               }
           }
       }.start();

    }

}
