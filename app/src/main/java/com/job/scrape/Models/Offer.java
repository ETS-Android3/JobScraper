package com.job.scrape.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Offer implements Parcelable {

    private int id;
    private String title, salary, postType, pubDate, location, link, experience;
    private Company company;
    private User user;
    private ArrayList<Integer> requirements;

    public Offer() { }

    public Offer(String title, String salary, String postType, String pubDate, String location, String link, String experience, ArrayList<Integer> requirements,Company company) {
        this.title = title;
        this.salary = salary;
        this.pubDate = pubDate;
        this.postType = postType;
        this.location = location;
        this.link = link;
        this.experience = experience;
        this.requirements = requirements;
        this.company = company;
    }

    public Offer(String title, String salary, String postType, String pubDate, String location, String link, String experience, String requirements,Company company) {
        this.title = title;
        this.salary = salary;
        this.pubDate = pubDate;
        this.postType = postType;
        this.location = location;
        this.link = link;
        this.experience = experience;
        this.requirements = new ArrayList<>();
        for (String requirement:requirements.split(" ")) {
            this.requirements.add(Integer.parseInt(requirement));
        }
        this.company = company;
    }

    protected Offer(Parcel in) {
        title = in.readString();
        salary = in.readString();
        pubDate = in.readString();
        postType = in.readString();
        location = in.readString();
        experience = in.readString();
        link = in.readString();
        company = in.readTypedObject(Company.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ArrayList<Integer> getRequirements() {
        return requirements;
    }

    public String getStringRequirements() {
        String sr = "";
        for (int i = 0; i < requirements.size()-1; i++) {
            sr += ","+requirements.get(i);
        }
        sr += ","+requirements.get(requirements.size()-1);

        return sr;
    }

    public void setRequirements(ArrayList<Integer> requirements) {
        this.requirements = requirements;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(salary);
        dest.writeString(pubDate);
        dest.writeString(postType);
        dest.writeString(location);
        dest.writeString(experience);
        dest.writeString(link);
        dest.writeTypedObject(company, flags);
    }
}

