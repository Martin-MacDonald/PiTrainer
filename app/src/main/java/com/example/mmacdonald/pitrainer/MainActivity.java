package com.example.mmacdonald.pitrainer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText mEmailEditText;
    EditText mPasswordEditText;
    Button mUserActionButton;
    TextView mFormActionTextView;
    int mFormType = 0;

    Intent mShowHomePageIntent;

    FirebaseAuth mAuth;

    public void submitForm(View view){

        if (mFormType == 0) {
            if (mEmailEditText.getText().toString().equals("") || mPasswordEditText.getText().toString().equals("")) {

                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();

            } else {

                mAuth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            startActivity(mShowHomePageIntent);

                        } else {

                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        } else {

            if (mEmailEditText.getText().toString().equals("") || mPasswordEditText.getText().toString().equals("")) {

                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();

            } else {

                mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            startActivity(mShowHomePageIntent);

                        }else{

                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }

        }

    }

    public void changeFormType(View view){

        if (mFormType == 0){

            mUserActionButton.setText(R.string.log_in_button);
            mFormActionTextView.setText(R.string.form_type_sign_up);
            mFormType = 1;

        } else {

            mUserActionButton.setText(R.string.sign_up_button);
            mFormActionTextView.setText(R.string.form_type_login);
            mFormType = 0;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mUserActionButton = (Button) findViewById(R.id.userActionButton);
        mFormActionTextView = (TextView) findViewById(R.id.formActionTextView);

        mShowHomePageIntent= new Intent(getApplicationContext(), HomePage.class);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){

            startActivity(mShowHomePageIntent);

        }
    }
}
