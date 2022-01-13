package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout insta,facebook,web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_about );

        insta = findViewById ( R.id.insta );
        facebook = findViewById ( R.id.facebook );
        web = findViewById ( R.id.web );


        insta.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( AboutActivity.this, "Hey there!", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.instagram.com/prajwal__ukhalkar/");
            }
        } );


        facebook.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( AboutActivity.this, "Hey there!", Toast.LENGTH_SHORT ).show ();
                gotourl("https://www.facebook.com/prajwal.ukhalkar/");
            }
        } );


        web.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( AboutActivity.this, "Hey there!", Toast.LENGTH_SHORT ).show ();
                gotourl("https://code-prajwal.vercel.app/");
            }
        } );


    }

    private void gotourl(String s) {
        Uri uri = Uri.parse ( s );
        startActivity ( new Intent (Intent.ACTION_VIEW,uri) );
    }
}