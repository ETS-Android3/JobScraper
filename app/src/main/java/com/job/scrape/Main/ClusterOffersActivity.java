package com.job.scrape.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.job.scrape.Adapters.OfferClustersAdapter;
import com.job.scrape.Adapters.OfferListAdapter;
import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Constants;
import com.job.scrape.Models.Cluster;
import com.job.scrape.Models.Offer;
import com.job.scrape.Models.User;
import com.job.scrape.R;
import com.job.scrape.Utils.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import weka.clusterers.EM;
import weka.core.Instance;

public class ClusterOffersActivity extends AppCompatActivity implements OfferClustersAdapter.OfferClustersClickListener {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/jobscrape";
    private List<Cluster> clusterList = new ArrayList<>();
    private OfferClustersAdapter offerClustersAdapter;
    private TextView btnViewCluster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_offers);

        File file = new File(path+"/cluster_model.model");
        try (FileInputStream fis = new FileInputStream(file)) {
            EM clusterer = (EM) weka.core.SerializationHelper.read(fis);
            System.out.println(clusterer);

            ArrayList<Offer> offerList = getIntent().getParcelableArrayListExtra("offers");
            System.out.println(offerList.get(0).getTitle());

            Cluster tmpCluster = new Cluster();
//            String rs = offerList.get(0).getStringRequirements();
//            clusterer.clusterInstance(rs);
            tmpCluster.setOfferList(offerList);
            tmpCluster.setClusterNum(1);

            clusterList.add(tmpCluster);

            initRecyclerView(clusterList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(List<Cluster> clusterList) {
        RecyclerView recyclerView = findViewById(R.id.clusterRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        offerClustersAdapter = new OfferClustersAdapter(clusterList, this);
        recyclerView.setAdapter(offerClustersAdapter);
    }

    @Override
    public void onClusterBtnClick(Cluster cluster) {

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