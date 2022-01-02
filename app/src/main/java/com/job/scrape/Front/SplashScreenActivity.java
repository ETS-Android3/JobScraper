package com.job.scrape.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.job.scrape.R;
import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Models.Admin;
import com.job.scrape.Models.User;
import com.job.scrape.Models.UserContext;
import com.job.scrape.Models.UserTypeState;


public class SplashScreenActivity extends AppCompatActivity {

    private UserContext userContext;
    private UserTypeState normalUser;
    public static UserTypeState adminUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userContext = new UserContext();
        normalUser = new User();
        adminUser = new Admin();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(welcome);

                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }
}