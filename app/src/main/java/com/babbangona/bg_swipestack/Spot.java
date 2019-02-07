package com.babbangona.bg_swipestack;

import android.support.annotation.NonNull;

public class Spot {

    private static long counter;

    private final long id;

    @NonNull
    private String city;

    @NonNull
    private String name;

    @NonNull
    private String url;

    public Spot(long counter, String name, String city, String url){
        this.city = city;
        this.name = name;
        this.url = url;
        this.id = counter;
    }


    public static long getCounter() {
        return counter;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

}
