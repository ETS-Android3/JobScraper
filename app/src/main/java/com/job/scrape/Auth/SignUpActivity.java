package com.job.scrape.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.job.scrape.Constants;
import com.job.scrape.Main.MainScreenActivity;
import com.job.scrape.R;
import com.job.scrape.Models.User;
//import com.parse.FindCallback;
//import com.parse.GetDataCallback;
//import com.parse.LogInCallback;
//import com.parse.ParseException;
//import com.parse.ParseFile;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;
//import com.parse.SignUpCallback;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUpActivity extends AppCompatActivity {

    private EditText userNameText , userPasswordText , userUsernameText, userEmailText;

    private Spinner spinner;
    private ArrayAdapter<String> jobArrayAdapter;
    private String [] jobs;
    private String [] defaultJobs;
    public String job;
    public static User mainUser;
    private Intent intent ;
//    private ImageView imageView;
//    Connection link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameText=findViewById(R.id.edittext_id_name_sign_up);
        userUsernameText=findViewById(R.id.edittext_id_username_sign_up);
        userPasswordText=findViewById(R.id.edittext_user_password_sign_up);
        userEmailText=findViewById(R.id.edittext_email_sign_up);
        spinner = findViewById(R.id.jobSpinner);

        defineJobSpinner();

    }

    public void defineJobSpinner(){

        jobs = new String[] {"Contractor","Doctor","Driver","Engineer","Entrepreneur","Farmer","Police","Soldier",
                "Sportsman","Student","Teacher","Waiter","Worker"};

        jobArrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,jobs);

        spinner.setAdapter(jobArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                job = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                job = null;
            }
        });
    }

    public void signUp(View view) {

        Task t = new Task();
        t.execute();

    }

    class Task extends AsyncTask<Void, Void, Void> {
        String error = "";
        int userId;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (userNameText.getText().toString() != null && userUsernameText.getText().toString() != null && userPasswordText.getText().toString() != null && userEmailText.getText().toString() != null && job != null) {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                    String query = "insert into `users`(`id`, `name`, `username`, `password`, `email`, `job`) values(null, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = link.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, userNameText.getText().toString());
                    pstmt.setString(2, userUsernameText.getText().toString());
                    pstmt.setString(3, userPasswordText.getText().toString());
                    pstmt.setString(4, userEmailText.getText().toString());
                    pstmt.setString(5, job);

                    int affetcedRows = pstmt.executeUpdate();
                    if (affetcedRows > 1) {
                        ResultSet rs = pstmt.getGeneratedKeys();
                        if(rs.next()) {
                            userId = rs.getInt(1);
                        } else {
                            error = "InsertionError";
                        }
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
            if (error.equals("")) {
                Toast.makeText(getApplicationContext(),"Congratulations! you signed up successfully.",Toast.LENGTH_SHORT).show();
                mainUser = new User(
                        userId,
                        userNameText.getText().toString(),
                        userUsernameText.getText().toString(),
                        userPasswordText.getText().toString(),
                        userEmailText.getText().toString(),
                        job
                );
                intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);

                SignUpActivity.this.finish();
                SignUpActivity.this.getParent().finish();
            } else {
                Toast.makeText(getApplicationContext(),"Oops! " + error, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(unused);
        }
    }
}