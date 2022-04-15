package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.job.scrape.Models.Offer;
import com.job.scrape.R;

import java.util.ArrayList;
import java.util.List;

public class InsightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

    }

    public void getRecommendation(View v) {
        startActivity(new Intent(InsightsActivity.this, RecommendationActivity.class));
    }

    public void getClusters(View v) {
        String json = "[{" +
                String.format("\"title\":\"%s\",", "Software Engineer")+
                String.format("\"salary\":\"%s\",", "For Free")+
                String.format("\"pubDate\":\"%s\",", "2022-01-01 00:00:00")+
                String.format("\"postType\":\"%s\",", "CDI")+
                String.format("\"location\":\"%s\",", "Kenitra")+
                String.format("\"experience\":\"%s\",", "5 years")+
                String.format("\"link\":\"%s\",", "http://localhost")+
                String.format("\"company\":%s", "{"+
                        String.format("\"name\":\"%s\",", "JobScrape")+
                        String.format("\"image\":\"%s\"", "https://www.rekrute.com/rekrute/file/entrepriseLogo/recruiter_id/4719")+
                        "}")+
                "}]";;

        Gson gson = new Gson();
        Offer[] offers = gson.fromJson(json, Offer[].class);
        ArrayList<Offer> offerList = new ArrayList<>();
        for (Offer offer:offers) {
            offerList.add(offer);
        }
        Intent intent = new Intent(InsightsActivity.this, ClusterOffersActivity.class);
        intent.putParcelableArrayListExtra("offers", offerList);
        startActivity(intent);
    }
}