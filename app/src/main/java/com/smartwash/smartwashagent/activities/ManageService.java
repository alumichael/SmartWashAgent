package com.smartwash.smartwashagent.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwash.smartwashagent.Model.Category.AddCategory;
import com.smartwash.smartwashagent.Model.Category.CategoryGetData;
import com.smartwash.smartwashagent.Model.Category.CategoryGetObj;
import com.smartwash.smartwashagent.Model.Category.DeleteCategory;
import com.smartwash.smartwashagent.Model.Category.UpdateCategory;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.LoginModel.UserGetObj;
import com.smartwash.smartwashagent.Model.LoginModel.UserPostData;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.Model.OnlyIDRequest;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderStatusHead;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.adapters.CardAdapter;
import com.smartwash.smartwashagent.adapters.ServiceAdapter;
import com.smartwash.smartwashagent.fragments.Fragment_Dashboard;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageService extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,
        ServiceAdapter.MessageAdapterListener{

    /** ButterKnife Code **/
    @BindView(R.id.manage_service_layout)
    LinearLayout mManageServiceLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.input_layout)
    LinearLayout mInputLayout;
    @BindView(R.id.inputLayoutServiceName)
    TextInputLayout mInputLayoutServiceName;
    @BindView(R.id.service_editxt)
    EditText mServiceEditxt;
    @BindView(R.id.inputLayoutPrice)
    TextInputLayout mInputLayoutPrice;
    @BindView(R.id.price_editxt)
    EditText mPriceEditxt;

    @BindView(R.id.contrl_text)
    TextView mContrlTxt;

    @BindView(R.id.add_button)
    Button mAddButton;
    @BindView(R.id.update_button)
    Button mUpdateButton;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mProgressbar;
    @BindView(R.id.not_found_layout)
    LinearLayout mNotFoundLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_service)
    RecyclerView mRecyclerService;

    /** ButterKnife Code **/

    UserPreferences userPreferences;
    String message="";
    String title="";

    View view;

    ProgressDialog dialog;

    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    private ServiceAdapter serviceAdapter;
    private List<CategoryGetData> ServiceList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_service);
        ButterKnife.bind(this);

        userPreferences=new UserPreferences(this);


        dialog=new ProgressDialog(this);

        Intent intent=getIntent();
        title=intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);
        init();

        mAddButton.setOnClickListener(this);
        mUpdateButton.setOnClickListener(this);



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

        getServices();

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
        getServices();
    }


    private void getServices(){

        if (networkConnection.isNetworkConnected(this)) {
            //get client and call object for request

            Call<CategoryGetObj> call = client.fetch_service_cat();
            call.enqueue(new Callback<CategoryGetObj>() {
                @Override
                public void onResponse(Call<CategoryGetObj> call, Response<CategoryGetObj> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: " + apiError.getErrors());
                            Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                            //Log.i("Invalid Entry", response.errorBody().toString());
                            mSwipeRefreshLayout.setRefreshing(false);

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch Failed");
                            mSwipeRefreshLayout.setRefreshing(false);

                        }

                        return;
                    }

                    mSwipeRefreshLayout.setRefreshing(false);

                    ServiceList = response.body().getData();


                    int count = ServiceList.size();


                    if (count == 0) {
                        showMessage("Empty Service");
                        mNotFoundLayout.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);

                    } else {
                        list();
                    }

                }


                @Override
                public void onFailure(Call<CategoryGetObj> call, Throwable t) {
                    showMessage("Fetch failed, check your internet " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

        }else{
            showMessage("No Internet connection discovered!");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void list(){
        serviceAdapter = new ServiceAdapter(this, ServiceList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        mRecyclerService.setLayoutManager(linearLayoutManager);
        mRecyclerService.setItemAnimator(new DefaultItemAnimator());
        mRecyclerService.setAdapter(serviceAdapter);
    }

    private void showMessage(String s) {
        Snackbar.make(mManageServiceLayout, s, Snackbar.LENGTH_LONG).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.add_service) {

            mServiceEditxt.setText("");
            mPriceEditxt.setText("");
            mContrlTxt.setText("Add Service");
            mUpdateButton.setVisibility(View.GONE);
            mAddButton.setVisibility(View.VISIBLE);
            mProgressbar.setVisibility(View.GONE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.add_button:
                validation();
                break;

            case R.id.update_button:
                validationUpdate();
                break;

        }


    }


    private void validationUpdate(){

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mServiceEditxt.getText().toString().isEmpty()) {
                mInputLayoutServiceName.setError("Service Name is required!");
                isValid = false;
            }  else {
                mInputLayoutServiceName.setErrorEnabled(false);
            }
            if (mPriceEditxt.getText().toString().isEmpty()) {
                mInputLayoutPrice.setError("Price is required!");
                isValid = false;
            }  else {
                mInputLayoutPrice.setErrorEnabled(false);
            }

            if (isValid) {

                //Post Request to Api
                updateService();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }




    private void validation(){

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mServiceEditxt.getText().toString().isEmpty()) {
                mInputLayoutServiceName.setError("Service Name is required!");
                isValid = false;
            }  else {
                mInputLayoutServiceName.setErrorEnabled(false);
            }
            if (mPriceEditxt.getText().toString().isEmpty()) {
                mInputLayoutPrice.setError("Price is required!");
                isValid = false;
            }  else {
                mInputLayoutPrice.setErrorEnabled(false);
            }

            if (isValid) {

                //Post Request to Api
                addService();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }



    private void addService(){

        mAddButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);

        AddCategory addCategory=new AddCategory(mServiceEditxt.getText().toString(),
                Integer.parseInt(mPriceEditxt.getText().toString()));

        Call<Message> call = client.add_service(addCategory);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code()==400){
                    showMessage("Check your internet connection");
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        if(response.code()==422){

                            showMessage("Invalid entry");
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);
                            return;
                        }

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Add Failed: " + apiError.getErrors());
                            Log.i("Invalid EntryK", apiError.getErrors().toString());
                            Log.i("Invalid Entry", response.errorBody().toString());

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            showMessage("Add Failed");

                        }
                        mAddButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        return;
                    }
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);

                    if(response.code()==200) {




                        String status=response.body().getStatus();
                        if(status.equals("success")) {
                            mServiceEditxt.setText("");
                            mPriceEditxt.setText("");

                            init();

                        }else{
                            message=response.body().getMessage();
                            showMessage(message);
                        }
                    }

                } catch (Exception e) {
                    showMessage("Add Failed: " + e.getMessage());
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    Log.i("Add Failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                if(message!=null){
                    showMessage(message);
                }else{
                    showMessage("Add Failed");
                }

                Log.i("GEtError", t.getMessage());
                mAddButton.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }


    private void updateService(){

        mUpdateButton.setVisibility(View.GONE);
        mAddButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);

        UpdateCategory updateCategory=new UpdateCategory(userPreferences.getId(),mServiceEditxt.getText().toString(),
                mPriceEditxt.getText().toString());

        Call<Message> call = client.update_service(updateCategory);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code()==400){
                    showMessage("Check your internet connection");
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        if(response.code()==422){

                            showMessage("Invalid entry");
                            mServiceEditxt.setText("");
                            mPriceEditxt.setText("");
                            mContrlTxt.setText("Add Service");
                            mUpdateButton.setVisibility(View.GONE);
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);
                            return;
                        }

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Update Failed: " + apiError.getErrors());
                            Log.i("Invalid EntryK", apiError.getErrors().toString());
                            Log.i("Invalid Entry", response.errorBody().toString());
                            mServiceEditxt.setText("");
                            mPriceEditxt.setText("");
                            mContrlTxt.setText("Add Service");
                            mUpdateButton.setVisibility(View.GONE);
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            showMessage("Update Failed");
                            mServiceEditxt.setText("");
                            mPriceEditxt.setText("");
                            mContrlTxt.setText("Add Service");
                            mUpdateButton.setVisibility(View.GONE);
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);

                        }
                        mUpdateButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        mServiceEditxt.setText("");
                        mPriceEditxt.setText("");
                        mContrlTxt.setText("Add Service");
                        mUpdateButton.setVisibility(View.GONE);
                        mAddButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        return;
                    }

                    mServiceEditxt.setText("");
                    mPriceEditxt.setText("");
                    mContrlTxt.setText("Add Service");
                    mUpdateButton.setVisibility(View.GONE);
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);



                    if(response.code()==200) {

                        String status=response.body().getStatus();
                        if(status.equals("success")) {

                            init();

                        }else{
                            message=response.body().getMessage();
                            showMessage(message);

                        }
                    }

                } catch (Exception e) {
                    showMessage("Update Failed: " + e.getMessage());
                    mServiceEditxt.setText("");
                    mPriceEditxt.setText("");
                    mContrlTxt.setText("Add Service");
                    mUpdateButton.setVisibility(View.GONE);
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    Log.i("Update Failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                if(message!=null){
                    showMessage(message);
                }else{
                    showMessage("Update Failed");
                }

                Log.i("GEtError", t.getMessage());
                mServiceEditxt.setText("");
                mPriceEditxt.setText("");
                mUpdateButton.setVisibility(View.GONE);
                mAddButton.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onDeleteClicked(int position) {

            dialogMessage();

            DeleteCategory deleteCategory=new DeleteCategory(position);

            Call<Message> call = client.delete_service(deleteCategory);

            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if(response.code()==400){
                        dismissDialog();
                        showMessage("Check your internet connection");
                        return;
                    }else if(response.code()==429){
                        dismissDialog();
                        showMessage("Too many requests on database");
                        return;
                    }else if(response.code()==500){
                        dismissDialog();
                        showMessage("Server Error");
                        return;
                    }else if(response.code()==401){
                        dismissDialog();
                        showMessage("Unauthorized access, please try login again");
                        return;
                    }


                    try {
                        if (!response.isSuccessful()) {

                            if(response.code()==422){
                                dismissDialog();
                                showMessage("Invalid entry");

                                return;
                            }

                            try {
                                APIError apiError = ErrorUtils.parseError(response);
                                dismissDialog();
                                showMessage("Deletion Failed: " + apiError.getErrors());
                                Log.i("Invalid EntryK", apiError.getErrors().toString());
                                Log.i("Invalid Entry", response.errorBody().toString());

                            } catch (Exception e) {
                                dismissDialog();
                                Log.i("InvalidEntry", e.getMessage());
                                showMessage("Deletion Failed");

                            }
                            return;
                        }

                        dismissDialog();
                        if(response.code()==200) {

                            String status=response.body().getStatus();
                            if(status.equals("success")) {

                                init();

                            }else{
                                message=response.body().getMessage();
                                showMessage(message);
                            }
                        }

                    } catch (Exception e) {
                        dismissDialog();
                        showMessage("Deletion Failed: " + e.getMessage());
                        Log.i("Deletion Failed", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    dismissDialog();
                    if(message!=null){
                        showMessage(message);
                    }else{
                        showMessage("Deletion Failed");
                    }

                    Log.i("GEtError", t.getMessage());

                }
            });

        }

    @Override
    public void onItemClicked(String cate_name, String cate_price, String cate_id) {


        mServiceEditxt.setText(cate_name);
        mPriceEditxt.setText(cate_price);
        mContrlTxt.setText("Update Service");

        mUpdateButton.setVisibility(View.VISIBLE);
        mAddButton.setVisibility(View.GONE);


    }


    private void dialogMessage() {
        dialog.setMessage("Deleting Service... please wait");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



}
