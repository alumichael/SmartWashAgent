package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.smartwash.smartwashagent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageSubAdmin extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subadmin);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);


    }

    private void applyToolbar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }
}
