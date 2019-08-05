package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultDialog extends DialogFragment {

    private static final String TAG = "ResultDialog";
    private Button ok;
    private TextView t1;

        public ResultDialog() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v =  inflater.inflate(R.layout.result_dialog, container, false);
            t1 = v.findViewById(R.id.score);
            ok = v.findViewById(R.id.ok_button);

            Bundle b2 = getArguments();
            String questions = b2.getString("total");
            String correct = b2.getString("correct");

            t1.setText("Your Score : "+correct+ "/" +questions);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);


                }
            });


            return v;
        }

    }


