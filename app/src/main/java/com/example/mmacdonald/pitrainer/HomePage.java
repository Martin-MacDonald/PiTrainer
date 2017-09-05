package com.example.mmacdonald.pitrainer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.mmacdonald.pitrainer.R.id.worldBestTextView;

public class HomePage extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    TextView mWorldBestTextView;

    public void getWorldBest(){

        mDatabaseReference.child("worldBest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    mWorldBestTextView = (TextView) findViewById(R.id.worldBestTextView);
                    mWorldBestTextView.setText(getString(R.string.world_best) + " " + dataSnapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getUserBest(){

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        MyDB db = new MyDB(this, userID);

        String highestScore = db.getHighestScore(userID);
        TextView userBestTextView = (TextView) findViewById(R.id.userBestTextView);
        userBestTextView.setText(getString(R.string.user_best) + " " + highestScore);

    }

    public void changeToPiPage(View view){

        Intent switchToPiPage;

        switch (view.getId()){
            case R.id.testButton:
                switchToPiPage = new Intent(this, TestSkillsActivity.class);
                break;
            case R.id.practiceButton:
                switchToPiPage = new Intent(this, TestSkillsActivity.class);
                break;
            case R.id.learnButton:
                switchToPiPage = new Intent(this, TestSkillsActivity.class);
                break;
            default:
                switchToPiPage = new Intent(this, HomePage.class);
        }

        startActivity(switchToPiPage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.extra_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent showSignInPage = new Intent(this, MainActivity.class);
                startActivity(showSignInPage);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar homePageToolbar = (Toolbar) findViewById(R.id.homePageToolbar);
        setSupportActionBar(homePageToolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        getWorldBest();
        getUserBest();

    }
}
