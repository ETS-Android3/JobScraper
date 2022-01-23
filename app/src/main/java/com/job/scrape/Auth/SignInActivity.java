package com.job.scrape.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.job.scrape.Constants;
import com.job.scrape.Front.WelcomeScreenActivity;
import com.job.scrape.Main.MainScreenActivity;
import com.job.scrape.Main.SkillsFormActivity;
import com.job.scrape.R;
import com.job.scrape.Models.User;
import com.job.scrape.Utils.FileHandler;
import com.job.scrape.Utils.LoadingDialog;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    public static User mainUser;
    public static ArrayList<User> allUsers;
    private EditText userPasswordText , userUsernameText;
    private Intent intent;
    private LoadingDialog loadingDialog;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/jobscrape";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in); // load screen
        userUsernameText = findViewById(R.id.edittext_username_sign_in);
        userPasswordText = findViewById(R.id.edittext_user_password_sign_in);
        loadingDialog = new LoadingDialog(SignInActivity.this);

        File dir = new File(path);
        dir.mkdirs();
    }

    public void signIn(View view){

        Task t = new Task();
        t.execute();

    }

    class Task extends AsyncTask<Void, Void, Void> {
        int userId;
        String userName, userJob, userEmail;
        String error = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.startLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                if (userUsernameText.getText().toString() != null && userPasswordText.getText().toString() != null) {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                    String query = "select * from `users` where username=? and password=?";
                    PreparedStatement pstmt = link.prepareStatement(query);
                    pstmt.setString(1, userUsernameText.getText().toString());
                    pstmt.setString(2, userPasswordText.getText().toString());

                    ResultSet resultSet = pstmt.executeQuery();
                    if (resultSet.first()) {
                        Log.d("id", resultSet.getString("id"));
                        Log.d("name", resultSet.getString("name"));
                        Log.d("pwd", resultSet.getString("password"));
                        userId = resultSet.getInt("id");
                        userName = resultSet.getString("name");
                        userEmail = resultSet.getString("email");
                        userJob = resultSet.getString("job");

                    } else {
                        error = "Wrong Username or Password.";
                    }
                } else {
                    error = "You missed some required fields.";
                }

            } catch (Exception e) {
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            loadingDialog.stopLoadingDialog();

            if (error.equals("")) {
                mainUser = new User(
                        userId,
                        userName,
                        userUsernameText.getText().toString(),
                        userPasswordText.getText().toString(),
                        userEmail,
                        userJob
                );

                String[] data = {String.valueOf(mainUser.getId()), mainUser.getUsername()};
                File file = new File(path+"/data.txt");
                FileHandler.saveFile(file, data);

                intent = new Intent(SignInActivity.this, SkillsFormActivity.class);
                startActivity(intent);

                SignInActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(),"Oops! " + error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}