package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.job.scrape.Adapters.OfferListAdapter;
import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Models.Company;
import com.job.scrape.Models.Offer;
import com.job.scrape.R;
import com.job.scrape.Utils.GFG;
import com.job.scrape.Warehouse.Extractor;
import com.job.scrape.Warehouse.Loader;
import com.job.scrape.Warehouse.ResultHandler;
import com.job.scrape.Warehouse.Transformer;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchScreenActivity extends AppCompatActivity implements OfferListAdapter.OfferListClickListener {

    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        searchText = findViewById(R.id.job_search_input);
    }

    public void searchOffers(View v){
        String title = searchText.getText().toString();
        Offer offer = new Offer(title, "salary", "postType", "pubDate", "location", "link", "experience", (ArrayList<Integer>) null, new Company(null, null));
        if (searchText.getText() != null) {
            try {
                List<Offer> offerList = this.getOfferList(offer);

                this.initRecyclerView(offerList);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString()+" : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Oops! you entered nothing.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView(List<Offer> offerList) {
        RecyclerView recyclerView = findViewById(R.id.searchResultRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OfferListAdapter adapter = new OfferListAdapter(offerList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Offer> getOfferList (Offer offer) throws IOException, ExecutionException, InterruptedException {
        String json;

        ResultHandler resultHandler = Extractor.getRekruteOffers(offer);
        if (resultHandler.exception != null) {
            Toast.makeText(getApplicationContext(), resultHandler.exception.toString(), Toast.LENGTH_SHORT).show();
            json = "[{" +
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
                    "}]";
        } else {
            List<Offer> listOfOffers = resultHandler.offerList;
            json = Transformer.list2Json(listOfOffers);
        }

        Gson gson = new Gson();
        Offer[] offers = gson.fromJson(json, Offer[].class);
        return Arrays.asList(offers);

    }

    @Override
    public void onItemClick(Offer offer) {

    }
}