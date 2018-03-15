package com.example.sambedlow.mycourseworkapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.widget.*;
import android.text.util.*;
import android.text.Html;
import android.view.View.OnClickListener;
import android.view.View;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            InputStream instream = getAssets().open("MovieReleases2018.txt");
            if (instream != null) {
                List<MovieRelease> upcoming = readJsonStream(instream);
                int numberofreleasesloaded = 0;
                TableLayout table = (TableLayout) findViewById(R.id.MovieReleases);
                for (MovieRelease release : upcoming )
                {
                    TableRow row=new TableRow(this);
                    TextView releasedate=new TextView(this);
                    TextView yearmade=new TextView(this);
                    releasedate.setText(release.getReleaseDate());
                    yearmade.setText(release.getYearMade()+"");

                    TextView title=new TextView(this);
                    String titlehref = "<a href=\" + release.getMovieLink() + \"";
                    String hreftext = ">" + release.getTitle() + "</a> ";
                    title.setText( Html.fromHtml( titlehref + hreftext));
                    title.setLinksClickable(true);
                    final String movielink = release.getMovieLink();

                    title.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent =
                                    new Intent(Intent.ACTION_VIEW, Uri.parse(movielink));
                            startActivity(browserIntent);
                        }
                    });

                    row.addView(releasedate);
                    row.addView(yearmade);
                    row.addView(title);
                    table.addView(row);
                    numberofreleasesloaded++;
                }
                //System.out.println(numberofreleasesloaded);

            }
        }
        catch (Exception e)
        {
            String error="";
            error=e.getMessage();
        }

    }

    public List<MovieRelease> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readReleasesArray(reader);
        } finally {
            reader.close();
        }
    }
    public List<MovieRelease> readReleasesArray(JsonReader reader) throws IOException {
        List<MovieRelease> releases = new ArrayList<MovieRelease>();

        reader.beginArray();
        while (reader.hasNext()) {
            releases.add(readMovieRelease(reader));
        }
        reader.endArray();
        return releases;
    }

    public MovieRelease readMovieRelease(JsonReader reader) throws IOException {
        String releasedate = "";
        String title = "";
        int yearmade = 2018;
        String movielink = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Release Date")) {
                releasedate = reader.nextString();
            } else if (name.equals("Title")) {
                title = reader.nextString();
            } else if (name.equals("Year Made")) {
                yearmade = reader.nextInt();
            } else if (name.equals("Movie Link")) {
                movielink = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new MovieRelease(title, releasedate, yearmade, movielink);
    }








}

