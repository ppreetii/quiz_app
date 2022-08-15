package com.example.ahmed.thequizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences s;
    SharedPreferences.Editor e;
    private EditText etrollno;
    private Button btnregister;
     String rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        s=getSharedPreferences("login",MODE_PRIVATE);
        e=s.edit();

        //etname=(EditText)findViewById(R.id.etname);
        etrollno=(EditText)findViewById(R.id.etusername);
        btnregister=(Button)findViewById(R.id.btnregister);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etrollno.getText().toString().isEmpty()){
                   /* ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Wait a moment...");
                    progressDialog.show();*/
                   rollno=etrollno.getText().toString().trim();
                    e.putString("loginid",rollno);
                    e.apply();
                    Toast.makeText(RegisterActivity.this, "Registration complete !!", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    //finish();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
