package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartwash.smartwashagent.R;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;

    @BindView(R.id.txt_desc)
    TextView txtDesc;
  /*  @BindView(R.id.txt_version)
    TextView txtVersion;*/


    // Animation
    Animation slide_from_up, blink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


            // load the animation
        slide_from_up = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_from_up);

            blink = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.blink);

            //start animation
            imgLogo.startAnimation(slide_from_up);

            txtDesc.startAnimation(blink);
            //txtVersion.startAnimation(blink);



            Thread myThread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);

                        //Go to SignIN
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();

    }


}
