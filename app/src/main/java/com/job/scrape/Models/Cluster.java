package com.job.scrape.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private ArrayList<Offer> offerList;
    private int clusterNum;

    public Cluster() { }

    public Cluster(int clusterNum, ArrayList<Offer> offerList) {
        this.offerList = offerList;
        this.clusterNum = clusterNum;
    }

    public ArrayList<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(ArrayList<Offer> offerList) {
        this.offerList = offerList;
    }

    public int getClusterNum() {
        return clusterNum;
    }

    public void setClusterNum(int clusterNum) {
        this.clusterNum = clusterNum;
    }

}
