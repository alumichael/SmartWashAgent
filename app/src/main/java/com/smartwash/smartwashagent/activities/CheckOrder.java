package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.OnlyIDRequest;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderList;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderStatusData;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderStatusHead;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.adapters.CheckOrderAdapter;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOrder extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.check_order_layout)
    LinearLayout mCheckOrderLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.not_found_layout)
    LinearLayout notFoundLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_order)
    RecyclerView mRecyclerOrder;

    LinearLayoutManager layoutManager;
    UserPreferences userPreferences;

    CheckOrderAdapter checkOrderAdapter;

    List<OrderStatusData> status_item;
    List<OrderList> order_item;

    String title="";


    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);

        Intent intent=getIntent();
        title=intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);

        init();

    }

    private void applyToolbar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }

    private void init() {

        getOrderStatus();

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        getOrderStatus();
    }


    private void getOrderStatus(){

        //get client and call object for request
        Call<OrderStatusHead> call=client.fetch_all_order_status();
        call.enqueue(new Callback<OrderStatusHead>() {
            @Override
            public void onResponse(Call<OrderStatusHead> call, Response<OrderStatusHead> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        mSwipeRefreshLayout.setRefreshing(false);

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");
                        mSwipeRefreshLayout.setRefreshing(false);

                    }

                    return;
                }
                mSwipeRefreshLayout.setRefreshing(false);

                status_item=response.body().getData();

                int count=status_item.size();

                Log.i("Re-SuccessSize", String.valueOf(status_item.size()));

                if(count==0){
                    notFoundLayout.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                }else {
                    notFoundLayout.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                    list();

                    Log.i("Success", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<OrderStatusHead> call, Throwable t) {
                showMessage("Fetch failed, Please try again");
                Log.i("GEtError",t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private  void list(){
        layoutManager = new LinearLayoutManager(this);
        mRecyclerOrder.setLayoutManager(layoutManager);
        checkOrderAdapter = new CheckOrderAdapter(this,status_item);
        mRecyclerOrder.setAdapter(checkOrderAdapter);
        checkOrderAdapter.notifyDataSetChanged();
    }

    private void showMessage(String s) {
        Snackbar.make(mCheckOrderLayout, s, Snackbar.LENGTH_LONG).show();
    }







}
