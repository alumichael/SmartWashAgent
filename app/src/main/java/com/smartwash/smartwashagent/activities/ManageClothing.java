package com.smartwash.smartwashagent.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwash.smartwashagent.Model.Category.AddCategory;

import com.smartwash.smartwashagent.Model.Category.DeleteCategory;
import com.smartwash.smartwashagent.Model.Category.UpdateCategory;
import com.smartwash.smartwashagent.Model.ClothList.AddCloth;
import com.smartwash.smartwashagent.Model.ClothList.ClothGetData;
import com.smartwash.smartwashagent.Model.ClothList.ClothGetObj;
import com.smartwash.smartwashagent.Model.ClothList.DeleteCloth;
import com.smartwash.smartwashagent.Model.ClothList.UpdateCloth;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.adapters.ClothingAdapter;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;

import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageClothing  extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,
        ClothingAdapter.MessageAdapterListener {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.clothing_layout)
    LinearLayout mClothingLayout;
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar mToolbar;
    @BindView(R.id.input_layout)
    LinearLayout mInputLayout;
    @BindView(R.id.contrl_text)
    TextView mContrlText;

    @BindView(R.id.service_editxt)
    EditText mClothEditxt;
    @BindView(R.id.cloth_type)
    Spinner mClothType;

    @BindView(R.id.price_editxt)
    EditText mPriceEditxt;
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
    @BindView(R.id.recycler_cloths)
    RecyclerView mRecyclerCloths;
    /**
     * ButterKnife Code
     **/

    UserPreferences userPreferences;
    String message = "";
    String title = "";

    View view;

    ProgressDialog dialog;

    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    private ClothingAdapter clothingAdapter;
    private List<ClothGetData> clothList;

    String clothTypeString;
    String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clothing);
        ButterKnife.bind(this);

        userPreferences = new UserPreferences(this);

        dialog = new ProgressDialog(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);
        init();

        mAddButton.setOnClickListener(this);
        mUpdateButton.setOnClickListener(this);
        clothTypeSpinner();


    }

    private void applyToolbar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }


    private void init() {

        getCloths();

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
        getCloths();
    }


    private void clothTypeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.cloth_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mClothType.setAdapter(staticAdapter);

        mClothType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String stringText = (String) parent.getItemAtPosition(position);
                if (stringText.equals("Normal Cloth")) {

                    mClothType.setVisibility(View.VISIBLE);
                    mClothType.setClickable(true);


                    //De-Visualizing the corporate form
                    mPriceEditxt.setVisibility(View.GONE);
                    mPriceEditxt.setClickable(false);


                } else if (stringText.equals("Special Cloth")) {

                    mClothType.setVisibility(View.VISIBLE);
                    mClothType.setClickable(true);


                    //Visualizing the price

                    mPriceEditxt.setVisibility(View.VISIBLE);
                    mPriceEditxt.setClickable(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mClothType.getItemAtPosition(0);
                //De-Visualizing the individual form
                mPriceEditxt.setVisibility(View.GONE);
                price = "0";

            }
        });

    }


    private void getCloths() {
        if (networkConnection.isNetworkConnected(this)) {

            //get client and call object for request
            Call<ClothGetObj> call = client.fetch_cloths();
            call.enqueue(new Callback<ClothGetObj>() {
                @Override
                public void onResponse(Call<ClothGetObj> call, Response<ClothGetObj> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed");
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
                    clothList = response.body().getData();

                    int count = clothList.size();

                    if (count == 0) {
                        showMessage("Empty Clothing");
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        mNotFoundLayout.setVisibility(View.VISIBLE);

                    } else {
                        list();
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                    }
                }


                @Override
                public void onFailure(Call<ClothGetObj> call, Throwable t) {
                    showMessage("Fetch failed, check your internet ");
                    Log.i("GEtError", t.getMessage());
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });

        } else {

            showMessage("No Internet connection discovered!");
            mSwipeRefreshLayout.setRefreshing(false);

        }


    }

    private void list() {
        clothingAdapter = new ClothingAdapter(this, clothList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        mRecyclerCloths.setLayoutManager(linearLayoutManager);
        mRecyclerCloths.setItemAnimator(new DefaultItemAnimator());
        mRecyclerCloths.setAdapter(clothingAdapter);
    }

    private void showMessage(String s) {
        Snackbar.make(mClothingLayout, s, Snackbar.LENGTH_LONG).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cloth, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.add_cloth) {

            mClothEditxt.setText("");
            mPriceEditxt.setText("");
            mClothType.getItemAtPosition(0);
            mContrlText.setText("Add Clothing");
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

        switch (v.getId()) {

            case R.id.add_button:
                validation();
                break;

            case R.id.update_button:
                validationUpdate();
                break;

        }


    }


    private void validationUpdate() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mClothEditxt.getText().toString().isEmpty()) {
                showMessage("Cloth Name is required!");

                isValid = false;
            }


            clothTypeString = mClothType.getSelectedItem().toString();
            if (clothTypeString.equals("Cloth Type") && mClothType.isClickable()) {
                showMessage("Select cloth type");
                isValid = false;
            }

            if (isValid) {
                //Post Request to Api
                updateCloth();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }


    private void validation() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mClothEditxt.getText().toString().isEmpty()) {
                showMessage("Cloth Name is required!");

                isValid = false;
            }


            clothTypeString = mClothType.getSelectedItem().toString();
            if (clothTypeString.equals("Cloth Type") && mClothType.isClickable()) {
                showMessage("Select cloth type");
                isValid = false;
            }

            if (isValid) {

                //Post Request to Api
                addCloth();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }


    private void addCloth() {

        mAddButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);

        if (clothTypeString.equals("Normal Cloth")) {
            price = "0";
        } else {
            price = mPriceEditxt.getText().toString();
        }


        AddCloth addCloth = new AddCloth(mClothEditxt.getText().toString(),
                price);

        Call<Message> call = client.add_cloth(addCloth);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 400) {
                    showMessage("Check your internet connection");
                    return;
                } else if (response.code() == 429) {
                    showMessage("Too many requests on database");
                    return;
                } else if (response.code() == 500) {
                    showMessage("Server Error");
                    return;
                } else if (response.code() == 401) {
                    showMessage("Unauthorized access, please try login again");
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        if (response.code() == 422) {

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

                    if (response.code() == 200) {


                        String status = response.body().getStatus();
                        if (status.equals("success")) {
                            mClothEditxt.setText("");
                            mPriceEditxt.setText("");

                            init();

                        } else {
                            message = response.body().getMessage();
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

                if (message != null) {
                    showMessage(message);
                } else {
                    showMessage("Add Failed");
                }

                Log.i("GEtError", t.getMessage());
                mAddButton.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }


    private void updateCloth() {

        mUpdateButton.setVisibility(View.GONE);
        mAddButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);


        if (clothTypeString.equals("Normal Cloth")) {
            price = "0";
        } else {
            price = mPriceEditxt.getText().toString();
        }

        UpdateCloth updateCloth = new UpdateCloth(Integer.parseInt(userPreferences.getId()), mClothEditxt.getText().toString(),
                price);

        Call<Message> call = client.update_cloth(updateCloth);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 400) {
                    showMessage("Check your internet connection");
                    return;
                } else if (response.code() == 429) {
                    showMessage("Too many requests on database");
                    return;
                } else if (response.code() == 500) {
                    showMessage("Server Error");
                    return;
                } else if (response.code() == 401) {
                    showMessage("Unauthorized access, please try login again");
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        if (response.code() == 422) {

                            showMessage("Invalid entry");
                            mClothEditxt.setText("");
                            mPriceEditxt.setText("");
                            mClothType.getItemAtPosition(0);
                            mContrlText.setText("Add Cloth");
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
                            mClothEditxt.setText("");
                            mPriceEditxt.setText("");
                            mClothType.getItemAtPosition(0);
                            mContrlText.setText("Add Cloth");
                            mUpdateButton.setVisibility(View.GONE);
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            showMessage("Update Failed");
                            mClothEditxt.setText("");
                            mPriceEditxt.setText("");
                            mClothType.getItemAtPosition(0);
                            mContrlText.setText("Add Cloth");
                            mUpdateButton.setVisibility(View.GONE);
                            mAddButton.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);

                        }
                        mUpdateButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        mClothEditxt.setText("");
                        mPriceEditxt.setText("");
                        mClothType.getItemAtPosition(0);
                        mContrlText.setText("Add Cloth");
                        mUpdateButton.setVisibility(View.GONE);
                        mAddButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        return;
                    }

                    mClothEditxt.setText("");
                    mPriceEditxt.setText("");
                    mClothType.getItemAtPosition(0);
                    mContrlText.setText("Add Cloth");
                    mUpdateButton.setVisibility(View.GONE);
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);


                    if (response.code() == 200) {

                        String status = response.body().getStatus();
                        if (status.equals("success")) {

                            init();

                        } else {
                            message = response.body().getMessage();
                            showMessage(message);

                        }
                    }

                } catch (Exception e) {
                    showMessage("Update Failed: " + e.getMessage());
                    mClothEditxt.setText("");
                    mPriceEditxt.setText("");
                    mClothType.getItemAtPosition(0);
                    mContrlText.setText("Add Cloth");
                    mUpdateButton.setVisibility(View.GONE);
                    mAddButton.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    Log.i("Update Failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                if (message != null) {
                    showMessage(message);
                } else {
                    showMessage("Update Failed");
                }

                Log.i("GEtError", t.getMessage());
                mClothEditxt.setText("");
                mPriceEditxt.setText("");
                mClothType.getItemAtPosition(0);
                mUpdateButton.setVisibility(View.GONE);
                mAddButton.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onDeleteClicked(int position) {

        dialogMessage();

        DeleteCloth deleteCloth = new DeleteCloth(position);

        Call<Message> call = client.delete_cloth(deleteCloth);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 400) {
                    dismissDialog();
                    showMessage("Check your internet connection");
                    return;
                } else if (response.code() == 429) {
                    dismissDialog();
                    showMessage("Too many requests on database");
                    return;
                } else if (response.code() == 500) {
                    dismissDialog();
                    showMessage("Server Error");
                    return;
                } else if (response.code() == 401) {
                    dismissDialog();
                    showMessage("Unauthorized access, please try login again");
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        if (response.code() == 422) {
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
                    if (response.code() == 200) {

                        String status = response.body().getStatus();
                        if (status.equals("success")) {

                            init();

                        } else {
                            message = response.body().getMessage();
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
                if (message != null) {
                    showMessage(message);
                } else {
                    showMessage("Deletion Failed");
                }

                Log.i("GEtError", t.getMessage());

            }
        });

    }

    @Override
    public void onItemClicked(String cate_name, String cate_price, String cate_id) {


        mClothEditxt.setText(cate_name);
        mPriceEditxt.setText(cate_price);
        mClothType.getItemAtPosition(0);
        mContrlText.setText("Update Cloth");
        mUpdateButton.setVisibility(View.VISIBLE);
        mAddButton.setVisibility(View.GONE);


    }


    private void dialogMessage() {
        dialog.setMessage("Deleting Cloth... please wait");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}

