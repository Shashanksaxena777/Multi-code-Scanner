package com.example.qrscanner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    VideoView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        animationView = findViewById(R.id.animation_view);

        // Set the video URI
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation);
        animationView.setVideoURI(videoUri);

        animationView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationView.start();
            }
        });

        // Start playing the video
        animationView.start();

        // ...
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Restart the animation when the activity becomes visible again
        if (animationView != null) {
            animationView.start();
        }


        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setOrientationLocked(true);
                integrator.setPrompt("Scan a barcode or QR code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Handle scan cancelled
            } else {

                String scanResult = result.getContents();

                if (!scanResult.isEmpty()) {

                    Intent intent = new Intent(MainActivity.this, QRactivity.class);

                    intent.putExtra("scanResult", scanResult);


                    startActivity(intent);

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        // Move the app to the background when the back button is pressed
        moveTaskToBack(true);
    }
}




