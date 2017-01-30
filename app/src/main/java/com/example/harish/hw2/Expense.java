package com.example.harish.hw2;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * Created by Harish on 9/14/2016.
 */
public class Expense implements Serializable{
    String name;
    int category;
    Double amount;
    String date;
    String uri;

    public Expense() {
    }

    public Expense(String name, int category, Double amount, String date, String uri) {

        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
