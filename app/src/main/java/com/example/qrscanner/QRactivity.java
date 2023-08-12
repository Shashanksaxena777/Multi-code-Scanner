package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;


public class QRactivity extends AppCompatActivity {

    TextView btntxt;
    Button home;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);
        btntxt =findViewById(R.id.btntxt);
        home =findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QRactivity.this, MainActivity.class);
                startActivity(i);


            }
        });


        Intent intent = getIntent();
        // Retrieve the scan result from the Intent
        String scanResult = intent.getStringExtra("scanResult");
        btntxt.setText(scanResult);
        if (URLUtil.isValidUrl(scanResult)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
            startActivity(browserIntent);
        } else {
            // Handle non-URL data by searching for it on Google
            query = scanResult;
            String url = "https://www.google.com/search?q=" + Uri.encode(query);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }




    }
}