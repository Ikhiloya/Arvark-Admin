package com.loya.android.arvark_admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.loya.android.arvark_admin.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView splashImage;
    private TextView companyName, rental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashImage = (ImageView) findViewById(R.id.splashscreen_image);
        companyName = (TextView) findViewById(R.id.company_name);
        rental = (TextView) findViewById(R.id.rental);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        splashImage.startAnimation(animation);
        companyName.startAnimation(animation);
        rental.startAnimation(animation);

        final Intent intent = new Intent(this, Login.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
    }

