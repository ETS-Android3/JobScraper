package com.job.scrape.Front;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Auth.SignUpActivity;
import com.job.scrape.R;
import com.job.scrape.Models.User;

import java.util.ArrayList;

public class WelcomeScreenActivity extends AppCompatActivity {

    public static User mainUser;
    public static ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome); //load screen

    }

    public void signUpBtnClick(View v) {
        Intent signUp = new Intent(WelcomeScreenActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }

    public void signInBtnClick(View v) {
        Intent signIn = new Intent(WelcomeScreenActivity.this, SignInActivity.class);
        startActivity(signIn);
    }

}