package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.smartwash.smartwashagent.Model.Card;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.adapters.CardAdapter;
import com.smartwash.smartwashagent.fragments.Fragment_Dashboard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.main_content)
    LinearLayout main_content;


    PermissionCheckClass mPermissionCheckClass;
    Fragment fragment;


    UserPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);



        mPermissionCheckClass = new PermissionCheckClass(this);
        if (!mPermissionCheckClass.checkPermission()){
            mPermissionCheckClass.requestPermission();
        }

        applyToolbar("Dashboard", "We make it clean.");


        fragment = new Fragment_Dashboard();
        showFragment(fragment);
    }


    private void applyToolbar(String title, String subtitle) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setElevation(0);

    }


    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {

            //    startActivity(new Intent(MainActivity.this,AboutActivity.class));

            return true;
        }/* else if (itemId == R.id.action_change_pass) {

          //  changePassword();
            return true;

        } */else if (itemId == R.id.action_share) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "SmartWash Mobile");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "We make it clean!");

            startActivity(Intent.createChooser(sharingIntent, "Share via"));


            return true;
        } else if (itemId == R.id.action_update) {

            goPlayStore();

            return true;
        }

        else if (itemId == R.id.action_logout) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/"));
        startActivity(intent);
    }

    /*private void changePassword() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_pass, null);
        builder.setView(dialogView);
        EditText oldPassword = dialogView.findViewById(R.id.oldpass);
        EditText newPassword = dialogView.findViewById(R.id.newpass);
        AVLoadingIndicatorView progressBar = dialogView.findViewById(R.id.progressbar);

        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (oldPassword.getText().toString().isEmpty() || oldPassword.getText().toString().trim().length() < 6) {
                    showMessage("Invalid Password, ensure at least 6 characters");
                    return;
                } else if (oldPassword.getText().toString().isEmpty() || oldPassword.getText().toString().trim().length() < 6) {
                    showMessage("Invalid Password, ensure at least 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                UserPassword userPassword = new UserPassword(oldPassword.getText().toString().trim(), newPassword.getText().toString().trim());

                ChangePassPost changePassPost = new ChangePassPost(userPassword);

                //change_password(changePassPost);
                Call<ResponseBody> call = client.change_password("Token " + userPreferences.getUserToken(), changePassPost);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {

                            try {
                                APIError apiError = ErrorUtils.parseError(response);

                                showMessage("Invalid Entry: " + apiError.getErrors());
                                Log.i("Invalid EntryK", apiError.getErrors().toString());
                                Log.i("Invalid Entry", response.errorBody().toString());

                            } catch (Exception e) {
                                Log.i("InvalidEntry", e.getMessage());
                                showMessage("Invalid Entry");

                            }

                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        progressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        showMessage("Password Changed Successfully");
                        startActivity(new Intent(MainActivity.this, SignIn.class));
                        finish();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showMessage("Login Failed " + t.getMessage());
                        Log.i("GEtError", t.getMessage());
                        //progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
*/

    private void showMessage(String s) {
        Snackbar.make(main_content, s, Snackbar.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
