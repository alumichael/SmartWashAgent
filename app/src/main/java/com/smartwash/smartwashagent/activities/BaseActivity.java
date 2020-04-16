package com.smartwash.smartwashagent.activities;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.smartwash.smartwashagent.R;


@SuppressLint({"Registered"})
public class BaseActivity extends AppCompatActivity {

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }


    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_to_bottom, R.anim.slide_from_up);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_up, R.anim.slide_to_bottom);
    }
}
