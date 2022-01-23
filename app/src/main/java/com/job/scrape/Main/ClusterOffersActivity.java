package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Constants;
import com.job.scrape.Models.User;
import com.job.scrape.R;
import com.job.scrape.Utils.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClusterOffersActivity extends AppCompatActivity {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/jobscrape";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_offers);

        File file = new File(path+"/data.txt");
        try {
            String[] data = FileHandler.loadFile(file);
            if (data.length > 0) {
                // Code
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}


//class Task extends AsyncTask<Integer, Void, Void> {
//    int userId;
//    String userName, userJob, userEmail, userUserName, password;
//    String error = "";
//
//    @Override
//    protected Void doInBackground(Integer... params) {
//        userId = params[0];
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);
//
//            String query = "select * from `users` where id=?";
//            PreparedStatement pstmt = link.prepareStatement(query);
//            pstmt.setInt(1, userId);
//
//            ResultSet resultSet = pstmt.executeQuery();
//            if (resultSet.first()) {
//                userId = resultSet.getInt("id");
//                userName = resultSet.getString("name");
//                userEmail = resultSet.getString("email");
//                userJob = resultSet.getString("job");
//                userUserName = resultSet.getString("username");
//                password = resultSet.getString("password");
//
//            } else {
//                error = "Wrong Username or Password.";
//            }
//
//        } catch (Exception e) {
//            error = e.toString();
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void unused) {
//        super.onPostExecute(unused);
//    }
//}