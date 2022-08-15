package com.example.ahmed.thequizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etuser,etpin;
    private Button btnlogin,btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etuser=(EditText)findViewById(R.id.etusername);
        etpin=(EditText)findViewById(R.id.etPin);
        btnlogin=(Button) findViewById(R.id.btnlogin);
        btnsignup=(Button) findViewById(R.id.btnsignup);

        /*String complete=getIntent().getStringExtra("complete");
        if (complete.equals("success")) {
            btnlogin.setEnabled(false);
            btnsignup.setEnabled(false);
        }*/

            btnlogin.setOnClickListener(new myclick());
            btnsignup.setOnClickListener(new myclick());


        //btnlogin.setOnClickListener(new myclick());
        //btnsignup.setOnClickListener(new myclick());

        /*String loginid=s.getString("loginid",null);
        if (loginid!=null)
        {
            startActivity(new Intent(LoginActivity.this,StartingScreenActivity.class));
            finish();
        }*/



    }
    public class myclick implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            if (view==btnlogin)
            {
                String login=etuser.getText().toString();
                String pin=etpin.getText().toString();

                if(login.equals("admin") && pin.equals("1234"))
                {
                    //e.putString("loginid",login);
                   // e.commit();
                    startActivity(new Intent(LoginActivity.this,StartingScreenActivity.class));
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid LoginID or password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
               // finish();
            }

        }
    }

}
