package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mProfileName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;

    private TextInputLayout mPhone;
    private Button submit;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mRegProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        mProfileName = findViewById(R.id.reg_name);
        mPhone = findViewById(R.id.reg_phone);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_pass);

        submit = findViewById(R.id.signup_button);

        mRegProgressDialog = new ProgressDialog(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String profileName = mProfileName.getEditText().getText().toString();
                    String email = mEmail.getEditText().getText().toString();
                    String phone = mPhone.getEditText().getText().toString();
                    String password = mPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(profileName) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(phone)) {
                    mRegProgressDialog.setTitle("Registering User");
                    mRegProgressDialog.setMessage("Please wait while we create your account");
                    mRegProgressDialog.setCanceledOnTouchOutside(false);
                    mRegProgressDialog.show();

                registerUser(profileName, email, phone, password);

        }}
        });
    }

    private void registerUser(final String profileName, final String email, final String phone, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();
                            String DeviceToken = FirebaseInstanceId.getInstance().getToken();



                            mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                            HashMap<String , String> mHashMap = new HashMap<>();//to store complex data
                            mHashMap.put("name", profileName);
                            mHashMap.put("email",email);
                            mHashMap.put("phone", phone);
                            mHashMap.put("password",password);
                            mHashMap.put("device_token",DeviceToken);
                            mHashMap.put("image","default");
                            mHashMap.put("thumb_image","default");

                            mDatabase.setValue(mHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mRegProgressDialog.dismiss();

                                        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });

                        } else {
                            mRegProgressDialog.hide();
                            Toast.makeText(RegisterActivity.this,"Cannot Signin,Please check the form and try again.",Toast.LENGTH_LONG).show();

                        }


                    }
                });



    }
}
