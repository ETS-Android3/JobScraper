package com.job.scrape.Warehouse;

import com.job.scrape.Models.Offer;

import java.util.List;

public class ResultHandler {
    public Exception exception;
    public List<Offer> offerList;

    public ResultHandler(Exception exception, List<Offer> offerList) {
        this.exception = exception;
        this.offerList = offerList;
    }
}
