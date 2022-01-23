package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.job.scrape.R;

public class InsightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

    }

    public void getRecommendation(View v) {
        startActivity(new Intent(InsightsActivity.this, RecommendationActivity.class));
    }
}