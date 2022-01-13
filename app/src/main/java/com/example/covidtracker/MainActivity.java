package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.api.CountryData;
import com.example.covidtracker.api.Utilities;
import com.google.android.material.card.MaterialCardView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests,date,rtd;
    private TextView todayConfirm,todayRecovered,todayDeath;
    private PieChart pieChart;
    private MaterialCardView confirm,active,rec,death,test;
    private ImageView me;

    private List<CountryData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        list = new ArrayList<> ();

        confirm = findViewById ( R.id.confirm );
        rec = findViewById ( R.id.rec );
        active = findViewById ( R.id.active );
        death = findViewById ( R.id.death );
        test = findViewById ( R.id.test );
        rtd = findViewById ( R.id.rtd );
        me = findViewById ( R.id.me );



        rtd.setSelected ( true );

        confirm.animate ().alpha ( 1f ).setDuration ( 3000 ).start ();

        me.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (MainActivity.this,AboutActivity.class) );
            }
        } );



        confirm.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( MainActivity.this, "Maintain Social Distancing", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.covid19india.org/");
            }
        } );

        active.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( MainActivity.this, "Use sanitizer", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.covid19india.org/");
            }
        } );

        rec.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( MainActivity.this, "Safety Measures", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/media-resources/science-in-5/episode-18---covid-19---immunity-after-recovery-from-covid-19");
            }
        } );

        death.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( MainActivity.this, "Worldometer", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.worldometers.info/coronavirus/");
            }
        } );

        test.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( MainActivity.this, "Get vaccinated fast if you don't ", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.cowin.gov.in/");
            }
        } );



        

        init();

        findViewById ( R.id.cname ).setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent (MainActivity.this,CountryActivity.class) );
            }
        } );



        Utilities.getApiInterface ().getCountryData ().enqueue ( new Callback<List<CountryData>> () {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll (response.body ()  );

                for (int i = 0;i<list.size ();i++){
                    if (list.get ( i ).getCountry ().equals ( "India" )){

                        int death = Integer.parseInt (  list.get ( i ).getTests ());
                        int confirm = Integer.parseInt (  list.get ( i ).getCases ());
                        int active = Integer.parseInt (  list.get ( i ).getActive ());
                        int recovered = Integer.parseInt (  list.get ( i ).getRecovered ());



                            totalConfirm.setText ( NumberFormat.getInstance ().format ( confirm ) );
                            totalActive.setText ( NumberFormat.getInstance ().format ( active ) );
                            totalRecovered.setText ( NumberFormat.getInstance ().format ( recovered ) );
                            totalDeath.setText ( NumberFormat.getInstance ().format ( death ) );


                        try {
                            todayDeath.setText ( NumberFormat.getInstance ().format ( Integer.parseInt ( list.get ( i ).getTodayDeath () ) ) );
                        } catch (NumberFormatException e) {
                            e.printStackTrace ();
                        }
                        todayConfirm.setText ( NumberFormat.getInstance ().format ( Integer.parseInt ( list.get ( i ).getTodayCases () ) ) );
                            todayRecovered.setText ( NumberFormat.getInstance ().format ( Integer.parseInt ( list.get ( i ).getTodayRecovered () ) ) );
                            totalTests.setText ( NumberFormat.getInstance ().format ( Integer.parseInt ( list.get ( i ).getTests () ) ) );


                        setText(list.get ( i ).getUpdated ());

                        pieChart.addPieSlice ( new PieModel ("Confirm",confirm,getResources ().getColor ( R.color.yellow ) ));
                        pieChart.addPieSlice ( new PieModel ("Active",active,getResources ().getColor ( R.color.blue ) ));
                        pieChart.addPieSlice ( new PieModel ("Recovered",recovered,getResources ().getColor ( R.color.green ) ));
                        pieChart.addPieSlice ( new PieModel ("death",death,getResources ().getColor ( R.color.red ) ));

                        pieChart.startAnimation ();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText ( MainActivity.this, "Error :"+t.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );

    }

    private void gotourl(String s) {
        Uri uri = Uri.parse ( s );
        startActivity ( new Intent (Intent.ACTION_VIEW,uri) );
    }


    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat ("MMM dd, yyyy");
        long ml = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance ();
        calendar.setTimeInMillis ( ml );

        date.setText ( "Updated at " +format.format ( calendar.getTime (  ) ) );
    }

    private void init(){
        totalConfirm = findViewById ( R.id.totalConfirm );
        totalActive = findViewById ( R.id.totalActive );
        totalRecovered = findViewById ( R.id.totalRecovered );
        totalDeath = findViewById ( R.id.totalDeath );
        totalTests = findViewById ( R.id.totalTests );

        todayConfirm = findViewById ( R.id.todayConfirm );
        todayRecovered = findViewById ( R.id.todayRecovered );
        todayDeath = findViewById ( R.id.todayDeath );

        date = findViewById ( R.id.date );

        pieChart = findViewById ( R.id.piechart );



    }

}