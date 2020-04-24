package com.smartwash.smartwashagent.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.Model.updateOrder;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcknowledgeActivity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.serviceform_toolbar)
    Toolbar toolBar;

    @BindView(R.id.acknowlegeLayout)
    LinearLayout acknowledgeLayout;

    @BindView(R.id.delivery_txt)
    TextView mDeliveredText;
    @BindView(R.id.customer_name)
    TextView mCustomerName;
    @BindView(R.id.customer_phone_no)
    TextView mCustomerPhoneNO;
    @BindView(R.id.customer_addr)
    TextView mCustomerAddr;

    @BindView(R.id.deliver_button)
    Button mDeliveredButton;


    @BindView(R.id.avi1)
    AVLoadingIndicatorView mProgressBar;

    String userId="";
    String orderId="";
    String statusUpdate="Delivered";

    String fullname="";
    String phone_no="";
    String address="";
    String date_time="";

    UserPreferences userPreferences;


    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ackowledge);
        ButterKnife.bind(this);
        applyToolbarChildren("Finished Wash");
        userPreferences=new UserPreferences(this);
        
        mProgressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        orderId= intent.getStringExtra(Constant.ORDER_ID);
        userId= intent.getStringExtra(Constant.CUSTOMER_ID);
        statusUpdate=intent.getStringExtra(Constant.ACKNWOLEDGE);
        fullname=intent.getStringExtra(Constant.NAME);
        phone_no=intent.getStringExtra(Constant.PHONE);
        address=intent.getStringExtra(Constant.ADDR1);
        date_time=intent.getStringExtra(Constant.DAY_PHONE);

        mCustomerName.setText(fullname);
        mCustomerPhoneNO.setText(phone_no);
        mCustomerAddr.setText(address);


        mDeliveredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateOrderByCustomer(orderId,userId,statusUpdate);
            }
        });



    }


    private void applyToolbarChildren(String title) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    private void showMessage(String s) {
        Snackbar.make(acknowledgeLayout, s, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(AcknowledgeActivity.this, MainActivity.class));
        this.finish();
        super.onBackPressed();
    }

    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deliver_button:

                UpdateOrderByCustomer(orderId,userId,statusUpdate);
                break;
                
        }
    }

    private void UpdateOrderByCustomer(String orderId,String userId,String status) {
        if (networkConnection.isNetworkConnected(this)) {

            mProgressBar.setVisibility(View.VISIBLE);
            mDeliveredButton.setVisibility(View.INVISIBLE);

            updateOrder updateOrder=new updateOrder(orderId,userId,status);

            //get client and call object for request
            Call<Message> call = client.updateOrderby_customer(updateOrder);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: " + apiError.getErrors());
                            Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));

                            //Log.i("Invalid Entry", response.errorBody().toString());

                            mProgressBar.setVisibility(View.GONE);
                            mDeliveredButton.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch Failed");
                            mProgressBar.setVisibility(View.GONE);
                            mDeliveredButton.setVisibility(View.VISIBLE);

                        }

                        return;
                    }

                    String status = response.body().getStatus();

                    if(status.equals("success")){
                      String message=response.body().getMessage();
                      SuccessAlert(message);

                    }else{

                        mProgressBar.setVisibility(View.GONE);
                        mDeliveredButton.setVisibility(View.VISIBLE);

                        String message=response.body().getMessage();
                        ErrorAlert(message);


                    }

                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    //showMessage("Debit failed, check your internet " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                    mProgressBar.setVisibility(View.GONE);
                    mDeliveredButton.setVisibility(View.VISIBLE);
                }
            });

        }else{

            showMessage("No Internet connection discovered!");
            mProgressBar.setVisibility(View.GONE);
            mDeliveredButton.setVisibility(View.VISIBLE);
        }


    }


    private void ErrorAlert(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Error ")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage(message)
                .setPositiveButton("Try-Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        UpdateOrderByCustomer(orderId,userId,statusUpdate);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                    }
                })
                .show();

    }


    private void SuccessAlert(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Done")
                .setIcon(R.drawable.ic_done_all_black_24dp)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        startActivity(new Intent(AcknowledgeActivity.this, MainActivity.class));
                        finish();

                    }
                })

                .show();

    }


}
