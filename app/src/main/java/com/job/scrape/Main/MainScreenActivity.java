package com.job.scrape.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.job.scrape.Adapters.OfferListAdapter;
import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Models.Offer;
import com.job.scrape.R;
import com.job.scrape.Utils.GFG;
import com.job.scrape.Warehouse.Extractor;
import com.job.scrape.Warehouse.Loader;
import com.job.scrape.Warehouse.ResultHandler;
import com.job.scrape.Warehouse.Transformer;

import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainScreenActivity extends AppCompatActivity implements OfferListAdapter.OfferListClickListener {

    TextView userNameText;
    EditText jobSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen); //load screen

        userNameText = findViewById(R.id.textView_user_name);
        jobSearchText = findViewById(R.id.job_search_input);

        userNameText.setText("Hi " + SignInActivity.mainUser.getName() + ",");

        try {
            List<Offer> offerList = this.getOfferList();

            this.initRecyclerView(offerList);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString()+" : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initRecyclerView(List<Offer> offerList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OfferListAdapter adapter = new OfferListAdapter(offerList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Offer> getOfferList () throws ExecutionException, InterruptedException {

        String lastScrapeDate = Extractor.getLastScrapeDate(SignInActivity.mainUser);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);

        GFG dateTimeDiff = new GFG();
        dateTimeDiff.findDifference(lastScrapeDate, currentDateTime);

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

        if (dateTimeDiff.difference_In_Hours > 12) {
            List<Document> rekruteDocumentList = Extractor.scrapeRekruteOffers("");

            if (rekruteDocumentList.size() > 0) {
                List<Offer> rekruteOfferList = Transformer.getRekruteOfferList(rekruteDocumentList);
                Loader.loadOffers(rekruteOfferList, "rekrute.com", null);
                json = Transformer.list2Json(rekruteOfferList);
            }
        } else {
            ResultHandler resultHandler = Extractor.getRekruteOffers(null);
            if (resultHandler.exception == null) {
                List<Offer> listOfOffers = resultHandler.offerList;
                json = Transformer.list2Json(listOfOffers);
            } else {
                Toast.makeText(getApplicationContext(), resultHandler.exception.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        Gson gson = new Gson();
        Offer[] offers = gson.fromJson(json, Offer[].class);
        return Arrays.asList(offers);

    }

    public void getSearchScreen(View v) {
        startActivity(new Intent(MainScreenActivity.this, SearchScreenActivity.class));
    }

    public void getInsightsScreen(View v) {
        startActivity(new Intent(MainScreenActivity.this, InsightsActivity.class));
    }

    @Override
    public void onItemClick(Offer offer) {
        Intent intent = new Intent(MainScreenActivity.this, SingleOfferActivity.class);
        intent.putExtra("offer", offer);
        startActivity(intent);
    }
}
