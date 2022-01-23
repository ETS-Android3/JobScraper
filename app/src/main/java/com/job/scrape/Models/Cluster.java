package com.job.scrape.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Cluster {

    private List<Offer> offerList;
    private int clusterNum;

    public Cluster() { }

    public Cluster(int clusterNum, List<Offer> offerList) {
        this.offerList = offerList;
        this.clusterNum = clusterNum;
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public int getClusterNum() {
        return clusterNum;
    }

    public void setClusterNum(int clusterNum) {
        this.clusterNum = clusterNum;
    }

}
