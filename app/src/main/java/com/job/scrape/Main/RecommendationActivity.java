package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.R;
import com.job.scrape.Utils.FileHandler;
import com.job.scrape.Utils.Recommender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class RecommendationActivity extends AppCompatActivity {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/jobscrape";
    private TextView recomsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recomsTextView = findViewById(R.id.app_name_recom1);

        File file = new File(path+"/listeRules");

        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<ArrayList<ArrayList<String>>> listRules = (ArrayList<ArrayList<ArrayList<String>>>) ois.readObject();

            ArrayList<String> skills = SignInActivity.mainUser.getSkills();

            ArrayList<String> recommendations = Recommender.recommande(listRules, skills);

            String recomsText = "";
            for (String recommendation:recommendations) {
                recomsText += recommendation.toUpperCase(Locale.ROOT);
                recomsText += "\n";
            }
            recomsTextView.setText(recomsText);

            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        File file = new File(path+"/listeRules");
//        try {
//            String[] data = FileHandler.loadFile(file);
//            if (data.length > 0) {
//                // Code
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}