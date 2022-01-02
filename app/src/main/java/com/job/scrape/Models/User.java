package com.job.scrape.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Stack;

public class User implements  UserTypeState{
    private int id;
    private String name, password, username , email, job;
    private ArrayList<String> skills;
    private Bitmap photo;
    private Stack<History> history;
    private boolean userType;


    public User(String name, String username, String password, String email, String job) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.job = job;
        this.skills = new ArrayList<>();
        this.history = new Stack<>();
        this.userType = false;
    }

    public User(int id, String name, String username, String password, String email, String job) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.job = job;
        this.skills = new ArrayList<>();
        this.history = new Stack<>();
        this.userType = false;
    }

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.history = new Stack<>();
        this.userType = false;
    }

    @Override
    public void TypeChange(User user) {
        this.userType=false;
    }

    public Stack<History> getHistory() {
        return history;
    }

    public void setHistory(Stack<History> history) {
        this.history = history;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }


}
