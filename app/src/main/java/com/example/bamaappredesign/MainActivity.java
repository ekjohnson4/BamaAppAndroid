package com.example.bamaappredesign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    EditText e1, e2;
    private EditText mPasswordView;
    private String uid;

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
        setUID(auth.getCurrentUser().getUid());
        if(auth.getCurrentUser()!=null){
            System.out.println("Already signed in");
            goToHomePage();
        }

    }

    //Visitor login button pressed
    public void visitorLogin(View v){
        findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.showdescriptiontitle).setVisibility(View.GONE);
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        findViewById(R.id.login_progress).setVisibility(View.GONE);
        findViewById(R.id.showdescriptiontitle).setVisibility(View.VISIBLE);
    }

    //Sign in button pressed, checks credentials in Firebase
    public void studentLogin(View v) {
        findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.showdescriptiontitle).setVisibility(View.GONE);
        //If fields are empty, throw error
        if (e1.getText().toString().equals("") && e2.getText().toString().equals("")) {
            findViewById(R.id.login_progress).setVisibility(View.GONE);
            findViewById(R.id.showdescriptiontitle).setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Must enter in Crimson email and password.", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(e1.getText().toString(), e2.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                goToHomePage();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthException) {
                                mPasswordView.requestFocus();
                                findViewById(R.id.login_progress).setVisibility(View.GONE);
                                findViewById(R.id.showdescriptiontitle).setVisibility(View.VISIBLE);
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

    private void goToHomePage(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        Bundle b = new Bundle();
        b.putInt("key", 1); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        findViewById(R.id.login_progress).setVisibility(View.GONE);
        findViewById(R.id.showdescriptiontitle).setVisibility(View.VISIBLE);
        finish();
    }

    public void setUID(String userId)  {
        this.uid = userId;
    }

    public String getUID() {
        return this.uid;
    }
}

