package com.job.scrape.Front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.job.scrape.Constants;
import com.job.scrape.Main.MainScreenActivity;
import com.job.scrape.Main.SkillsFormActivity;
import com.job.scrape.R;
import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Models.Admin;
import com.job.scrape.Models.User;
import com.job.scrape.Models.UserContext;
import com.job.scrape.Models.UserTypeState;
import com.job.scrape.Utils.FileHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.job.scrape.Auth.SignInActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private UserContext userContext;
    private UserTypeState normalUser;
    public static UserTypeState adminUser;
    private Intent intent;

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
                intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }
}