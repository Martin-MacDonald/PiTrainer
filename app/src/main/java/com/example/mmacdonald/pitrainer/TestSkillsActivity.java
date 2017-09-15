package com.example.mmacdonald.pitrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.mmacdonald.pitrainer.R.id.scoreTextView;

public class TestSkillsActivity extends AppCompatActivity {

    TextView answerTextView;
    TextView scoreTextView;
    Spinner testLengthSpinner;
    String answer = "3.";
    final int TEST_SEGMENT = 10;
    LinearLayout testVariablesLinearLayout;
    GridLayout numberGridLayout;
    int testValue;
    String testString;
    ArrayList<String> testStringArray;
    Pi test;
    int checkDigitIndex = 0;
    int numberCorrectDigits = 0;

    public void checkIfComplete(){

        if (checkDigitIndex == testString.length()){

            Toast.makeText(this, "You won", Toast.LENGTH_SHORT).show();

        }

    }

    public void reset(){

        if (testVariablesLinearLayout.getVisibility() == View.VISIBLE) {
            testVariablesLinearLayout.setVisibility(View.GONE);
            numberGridLayout.setVisibility(View.VISIBLE);
            answerTextView.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);
            checkDigitIndex = 0;
            numberCorrectDigits = 0;
            scoreTextView.setText(Integer.toString(numberCorrectDigits));
            answer = "3.";
            answerTextView.setText(answer);
        } else {
            testVariablesLinearLayout.setVisibility(View.VISIBLE);
            numberGridLayout.setVisibility(View.GONE);
            answerTextView.setVisibility(View.GONE);
            scoreTextView.setVisibility(View.GONE);
            testLengthSpinner.setSelection(0);
        }


    }

    public void updateAnswer(String answerChar){

        answer += answerChar;
        answerTextView.setText(answer);

        scoreTextView.setText(Integer.toString(numberCorrectDigits));

    }

    public void checkDigit(View view){

        if (checkDigitIndex < testString.length()) {
            if (view.getTag().toString().equals(testStringArray.get(checkDigitIndex))) {
                Log.i("Answer", "Correct");
                checkDigitIndex++;
                numberCorrectDigits++;
                updateAnswer(view.getTag().toString());
            } else {
                Log.i("Answer", "Wrong");
            }
        }

        checkIfComplete();
    }

    public void startTest(View view){

        reset();
        testStringArray = new ArrayList<>();
        testStringArray.clear();

        testValue = (int) testLengthSpinner.getSelectedItem();
        testString = test.getPi_String(testValue);
        for (int i = 0; i < testValue; i++){

            testStringArray.add(Character.toString(test.getPi_String(testValue).charAt(i)));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.test_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                Intent homePageIntent = new Intent(this, HomePage.class);
                startActivity(homePageIntent);
            case R.id.restart:
                reset();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_skills);

        answerTextView = (TextView) findViewById(R.id.answerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        Toolbar testPageToolbar = (Toolbar) findViewById(R.id.testPageToolbar);
        setSupportActionBar(testPageToolbar);
        numberGridLayout = (GridLayout) findViewById(R.id.numberGridLayout);
        test = new Pi();

        testLengthSpinner = (Spinner) findViewById(R.id.testLengthSpinner);
        testVariablesLinearLayout = (LinearLayout) findViewById(R.id.testVariablesLinearLayout);

        ArrayList<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i <= test.getPI_MASTER().length()/TEST_SEGMENT; i++){

            spinnerArray.add(i * 10);

        }

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        testLengthSpinner.setAdapter(spinnerAdapter);


    }
}
