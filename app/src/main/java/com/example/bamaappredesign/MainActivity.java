package com.example.bamaappredesign;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText e1, e2;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        e1 = findViewById(R.id.email);
        e2 = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();
        mPasswordView = findViewById(R.id.password);
    }

    //Visitor login button pressed
    public void visitorLogin(View v){
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    //Sign in button pressed, checks credentials in Firebase
    public void studentLogin(View v) {
        //If fields are empty, throw error
        if (e1.getText().toString().equals("") && e2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Must enter in Crimson email and password.", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(e1.getText().toString(), e2.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                Bundle b = new Bundle();
                                b.putInt("key", 1); //Your id
                                intent.putExtras(b); //Put your id to your next Intent
                                startActivity(intent);
                                finish();
                            } else {
                              //  mPasswordView.setError("Password incorrect.");
                                //mPasswordView.requestFocus();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthException) {
                                mPasswordView.requestFocus();
                                if(((FirebaseAuthException) e).getErrorCode().equals("ERROR_WRONG_PASSWORD")){
                                    mPasswordView.setError("Password incorrect.");
                                }
                                else{
                                    System.out.println("Error code test:" + ((FirebaseAuthException) e).getErrorCode());
                                    mPasswordView.setError("Unable to login. Please try again.");
                                }
                                mPasswordView.requestFocus();
                            }
                        }
                    });

        }
    }
}

