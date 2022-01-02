package com.job.scrape.Warehouse;

import android.os.AsyncTask;

import com.job.scrape.Auth.SignInActivity;
import com.job.scrape.Constants;
import com.job.scrape.Models.Offer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Loader {

    public static void loadOffers(List<Offer> rekruteOfferList, String site, String field) {

        Record record = new Record(rekruteOfferList, site, field);
        StoreTask t = new StoreTask();
        t.execute(record);

    }

    static class StoreTask extends AsyncTask<Record, Void, Void> {
        String error = "";

        @Override
        protected Void doInBackground(Record... records) {
            try {
                if (records.length > 0 && records[0].offerList.size() > 0) {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection link = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);

                    List<Offer> offers = records[0].offerList;
                    String site = records[0].site;
                    String field = records[0].field;

                    for (Offer offer:offers) {
                        String requirements ="";
                        for(int id : offer.getRequirements()){
                            requirements += id + " ";
                        }

                        String query = "insert into `offers`(`id`, `title`, `salary`, `pub_date`, `post_type`, `location`, `experience`, `link`, `requirements`, `post_count`, `site`, `field`, `user_id`, `company_name`, `image`) values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmt = link.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        pstmt.setString(1, offer.getTitle());
                        pstmt.setString(2, offer.getSalary());
                        pstmt.setString(3, offer.getPubDate());
                        pstmt.setString(4, offer.getPostType());
                        pstmt.setString(5, offer.getLocation());
                        pstmt.setString(6, offer.getExperience());
                        pstmt.setString(7, offer.getLink());
                        pstmt.setString(8, requirements);
                        pstmt.setString(9, "1");
                        pstmt.setString(10, site);
                        pstmt.setString(11, field);
                        pstmt.setInt(12, SignInActivity.mainUser.getId());
                        pstmt.setString(13, offer.getCompany().getName());
                        pstmt.setString(14, offer.getCompany().getImage());

                        pstmt.executeUpdate();
                    }

                } else {
                    error = "Expected one offer at least, but 0 were given!";
                }
            } catch (Exception e) {
                error = e.toString() + " : " + e.getMessage();
            }
            return null;
        }
    }
}

class Record {
    public List<Offer> offerList;
    public String site;
    public String field;

    public Record(List<Offer> offerList, String site, String field) {
        this.offerList = offerList;
        this.site = site;
        this.field = field;
    }
}
