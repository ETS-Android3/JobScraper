package com.job.scrape.Warehouse;

import android.os.AsyncTask;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Constants;
import com.job.scrape.Models.Company;
import com.job.scrape.Models.Offer;
import com.job.scrape.Models.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Extractor {

    public static List<Document> scrapeRekruteOffers(String jobTitle) throws ExecutionException, InterruptedException {

        ScarpeTask t = new ScarpeTask();
        List<Document> documentList = t.execute(jobTitle).get();

        return documentList;
    }

    public static ResultHandler getRekruteOffers(Offer offer) throws ExecutionException, InterruptedException {
        SelectTask t = new SelectTask();
        ResultHandler resultHandler = t.execute(offer).get();

        return resultHandler;
    }

    public static String getLastScrapeDate(User user) throws ExecutionException, InterruptedException {
        LastScrapeTimeTask t = new LastScrapeTimeTask();
        String date = t.execute(user).get();

        return date;
    }

    static class ScarpeTask extends AsyncTask<String, Void, List<Document>> {
//        Document document = null;
        String error = null;

        @Override
        protected List<Document> doInBackground(String... args) {
            try {
                //scrap Jobs
//                String first_url = "https://www.rekrute.com/offres.html?s=1&p=2&o=1";
//                String[] keys = args[0].split(" ");
//                int sizeKeys = keys.length;
//                String secondary = "";
//                int i = 0;
//                for(String str : keys){
//                    if(i < sizeKeys -1){
//                        secondary+=str+"+";
//                    }
//                    else{
//                        secondary+=str;
//                    }
//                    i++;
//                }
//                String base_url = first_url+secondary+"&keyword="+secondary ;
//
                List<Document> documentList = new ArrayList<>();
//
//                String s = "1234567891";
//                for (int l = 0; l <= 2; l++) {
//                    StringBuilder url1 = new StringBuilder(base_url);
//                    char b = s.charAt(l);
//                    url1.setCharAt(38, b);
//                    int foi = 10;
//                    if(l == 2){
//                        foi = 2;
//                    }
//                    else if (l == 1){
//                        foi = 4;
//                    }
//                    for (int k = 0; k <=foi ; k++) {
//                        StringBuilder url = new StringBuilder(url1.toString());
//
//                        if(k == foi){
//                            url.setCharAt(42,'1');
//                            url.append('0');
//                        }else{
//                            char c = s.charAt(k);
//
//                            url.setCharAt(42, c);
//                        }
//                        Document document = Jsoup.connect(url.toString()).get();
//                        documentList.add(document);
//                    }
//                }
                String url = "https://www.rekrute.com/offres.html?s=1&p=2&o=1";

                Document document = Jsoup.connect(url).get();
                documentList.add(document);

                return documentList;
            } catch (IOException e) {
                error = e.getMessage();
                return null;
            }
        }

    }

    static class SelectTask extends AsyncTask<Offer, Void, ResultHandler> {

        @Override
        protected ResultHandler doInBackground(Offer... offers) {
            Offer baseOffer = offers[0];
            try {
                ArrayList<Offer> offerList = new ArrayList<>();
                Class.forName("com.mysql.jdbc.Driver");
                Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                PreparedStatement pstmt;
                if (baseOffer != null) {
                    String query = "select * from `offers` where `title` LIKE ?";
                    pstmt = link.prepareStatement(query);
                    pstmt.setString(1, "%"+baseOffer.getTitle()+"%");
                } else {
                    String query = "select * from `offers`";
                    pstmt = link.prepareStatement(query);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Offer offer = new Offer(rs.getString("title"), rs.getString("salary"), rs.getString("post_type"), rs.getString("pub_date"), rs.getString("location"), rs.getString("link"), rs.getString("experience"), rs.getString("requirements"), new Company(rs.getString("company_name"), rs.getString("image")));
                    offerList.add(offer);
                }
                rs.close();
                pstmt.close();
                return new ResultHandler(null, offerList);
            } catch (Exception e) {
                return new ResultHandler(e, null);
            }
        }
    }

    static class LastScrapeTimeTask extends AsyncTask<User, Void, String> {
        String error = "";

        @Override
        protected String doInBackground(User... users) {
            User user = users[0];
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                String query = "SELECT `scrape_date` FROM `offers` WHERE `user_id`=? ORDER BY `id` DESC LIMIT 1";
                PreparedStatement pstmt = link.prepareStatement(query);
                pstmt.setInt(1, user.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("scrape_date");
                }
                return null;
            } catch (Exception e) {
                return e.toString() + " : " + e.getMessage();
            }
        }
    }
}