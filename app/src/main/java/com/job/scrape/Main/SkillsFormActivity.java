package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Constants;
import com.job.scrape.Models.Offer;
import com.job.scrape.Models.User;
import com.job.scrape.R;
import com.job.scrape.Utils.LoadingDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SkillsFormActivity extends AppCompatActivity {

    EditText skillsText;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_form);

        skillsText = findViewById(R.id.skillsText);
        loadingDialog = new LoadingDialog(this);

        try {
            GetSkillsTask t = new GetSkillsTask();
            ArrayList<String> skills = t.execute(SignInActivity.mainUser).get();
            if (skills.size() > 0) {
                SignInActivity.mainUser.setSkills(skills);
                startActivity(new Intent(SkillsFormActivity.this, MainScreenActivity.class));
                SkillsFormActivity.this.finish();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void storeUserSkills(View view) {
        StoreSkillsTask t = new StoreSkillsTask();
        try {
            String error = t.execute(SignInActivity.mainUser).get();
            ArrayList<String> skills = new ArrayList<>();
            if (error != null) {
                Toast.makeText(getApplicationContext(),"Oops! " + error, Toast.LENGTH_SHORT).show();
            } else {
                for (String skill:skillsText.getText().toString().split(",")) {
                    skills.add(skill);
                }
                SignInActivity.mainUser.setSkills(skills);
                startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
                SkillsFormActivity.this.finish();
            }
        } catch (ExecutionException|InterruptedException e) {
            Toast.makeText(getApplicationContext(),"Oops! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void skipStep(View v){
        startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
        SkillsFormActivity.this.finish();
    }

    class StoreSkillsTask extends AsyncTask<User, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.startLoadingDialog();
        }

        @Override
        protected String doInBackground(User... users) {
            if (skillsText.getText() == null) {
                return "One skill at least is required!";
            } else {
                User user = users[0];
                for (String skill:skillsText.getText().toString().split(",")) {
                    String query = "insert into `user_skills`(`id`, `user_id`, `skill`) values(null, ?, ?)";
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                        PreparedStatement pstmt = link.prepareStatement(query);
                        pstmt.setInt(1, user.getId());
                        pstmt.setString(2, skill);

                        pstmt.executeUpdate();
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                }
                return null;

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loadingDialog.stopLoadingDialog();
        }
    }

    class GetSkillsTask extends AsyncTask<User, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.startLoadingDialog();
        }

        @Override
        protected ArrayList<String> doInBackground(User... users) {
            User user = users[0];
            ArrayList<String> skills = new ArrayList<>();
            String query = "select `skill` from `user_skills` where `user_id`=?";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                PreparedStatement pstmt = link.prepareStatement(query);
                pstmt.setInt(1, user.getId());

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    skills.add(rs.getString(1));
                }
                return skills;
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            loadingDialog.stopLoadingDialog();
        }
    }
}