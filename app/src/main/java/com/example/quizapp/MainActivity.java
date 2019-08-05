package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;
    TextView myTextView;

    long diff;
    long oldLong;
    long NewLong;
    Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.main_toolbar);
        myTextView = findViewById(R.id.timer);
        start = findViewById(R.id.start_button);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Quizzicle");

       start.setVisibility(View.INVISIBLE);

        countDownStart();
    }
    public void countDownStart() {

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
                    String oldTime = "28.02.2018, 12:00";//Timer date 1
                    String NewTime = "28.02.2018, 12:01";//Timer date 2
                    Date oldDate = null, newDate = null;
                    oldDate = formatter.parse(oldTime);
                    newDate = formatter.parse(NewTime);
                    oldLong = oldDate.getTime();
                    NewLong = newDate.getTime();
                    diff = NewLong - oldLong;

                    MyCount counter = new MyCount(diff, 1000);
                    counter.start();


                    if (newDate.after(oldDate)) {
                        start.postDelayed(new Runnable() {
                            public void run() {
                                start.setVisibility(View.VISIBLE);
                            }
                        }, diff);
                        start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

       if(currentUser == null){
           sendToStart();
        }

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        /*GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if(account == null){
            sendToStart();
        }*/

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu) ;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }


        return true;
    }

    private void sendToStart() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public class MyCount extends CountDownTimer {
        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            myTextView.setText("done!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = (TimeUnit.MILLISECONDS.toDays(millis)) + "Day "
                    + (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)) + ":")
                    + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) + ":"
                    + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            myTextView.setText(/*context.getString(R.string.ends_in) + " " +*/ hms);
        }
    }


}
