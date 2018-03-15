package com.example.sambedlow.mycourseworkapplication;

/**
 * Created by sambedlow on 15/03/2018.
 */

public class MovieRelease {

    String title;
    String releasedate;
    int yearmade;
    String movielink;
    public MovieRelease(String title, String releasedate, int yearmade, String movielink) {
        this.title = title;
        this.releasedate = releasedate;
        this.yearmade = yearmade;
        this.movielink= movielink;
    }
    public String getTitle(){
        return title;
    }
    public String getReleaseDate(){
        return releasedate;
    }
    public int getYearMade(){
        return yearmade;
    }
    public String getMovieLink(){
        return movielink;
    }
}


