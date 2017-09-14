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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TestSkillsActivity extends AppCompatActivity {

    Spinner testLengthSpinner;
    final int TEST_MAX = 1000;
    final int TEST_SEGMENT = 10;
    LinearLayout testVariablesLinearLayout;
    int testValue;
    String testString;
    ArrayList<String> testStringArray;

    public void startTest(View view){

        testStringArray = new ArrayList<>();
        testStringArray.clear();
        testVariablesLinearLayout.setVisibility(View.GONE);
        testValue = (int) testLengthSpinner.getSelectedItem();
        Pi test = new Pi();
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
                testVariablesLinearLayout.setVisibility(View.VISIBLE);
                testLengthSpinner.setSelection(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_skills);

        Toolbar testPageToolbar = (Toolbar) findViewById(R.id.testPageToolbar);
        setSupportActionBar(testPageToolbar);

        testLengthSpinner = (Spinner) findViewById(R.id.testLengthSpinner);
        testVariablesLinearLayout = (LinearLayout) findViewById(R.id.testVariablesLinearLayout);

        ArrayList<Integer> spinnerArray = new ArrayList<>();
        for (int i = 1; i <= TEST_MAX/TEST_SEGMENT; i++){

            spinnerArray.add(i * 10);

        }

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        testLengthSpinner.setAdapter(spinnerAdapter);


    }
}
