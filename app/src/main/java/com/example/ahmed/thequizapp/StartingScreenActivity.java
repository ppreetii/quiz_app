package com.example.ahmed.thequizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartingScreenActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
      private Button buttonStartQuiz;
    private int highscore;

   // DatabaseReference databaseReference;
    SharedPreferences s;
    String rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        s=getSharedPreferences("login",MODE_PRIVATE);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        loadHighscore();

         buttonStartQuiz = findViewById(R.id.button_start_quiz);
         buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonStartQuiz.getText().toString().equals("Start Quiz"))
                startQuiz();
                else if (buttonStartQuiz.getText().toString().equals("LogOut"))
                    saveScore(highscore);

            }
        });
    }

    private void saveScore(final int highscore) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Saving your score...");
        progressDialog.show();
        rollno=s.getString("loginid","nil");
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(rollno);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    databaseReference.setValue(highscore);
                    progressDialog.dismiss();
                    Toast.makeText(StartingScreenActivity.this, "your score  is saved to database. ", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(StartingScreenActivity.this,LoginActivity.class);
                    //intent.putExtra("complete","success");
                    startActivity(intent);
                    finish();

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(StartingScreenActivity.this, "Record already exists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(StartingScreenActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startQuiz() {
        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                final int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);

                }
            }
        }
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
        buttonStartQuiz.setText("LogOut");
    }
}
