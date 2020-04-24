package com.smartwash.smartwashagent.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.smartwash.smartwashagent.Model.Banner.Addbanner;
import com.smartwash.smartwashagent.Model.Banner.BannerGetData;
import com.smartwash.smartwashagent.Model.Banner.BannerGetObj;
import com.smartwash.smartwashagent.Model.Banner.DeleteBanner;
import com.smartwash.smartwashagent.Model.Category.AddCategory;
import com.smartwash.smartwashagent.Model.Category.DeleteCategory;
import com.smartwash.smartwashagent.Model.Category.UpdateCategory;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.adapters.BannerAdapter;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBanner extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,
        BannerAdapter.MessageAdapterListener{

    /** ButterKnife Code **/
    @BindView(R.id.manage_banner_layout)
    LinearLayout mManageBannerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.upload_button)
    Button mUploadButton;


    @BindView(R.id.contrl_text)
    TextView mContrlTxt;

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mProgressbar;
    @BindView(R.id.not_found_layout)
    LinearLayout mNotFoundLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_banner)
    RecyclerView mRecyclerBanner;

    /** ButterKnife Code **/

    UserPreferences userPreferences;
    String message="";
    String title="";

    View view;

    ProgressDialog dialog;

    int PICK_IMAGE = 1;
    Uri banner_img_uri;
    String banner_img_url;

    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    private BannerAdapter bannerAdapter;
    private List<BannerGetData> bannerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_banner);
        ButterKnife.bind(this);

        userPreferences=new UserPreferences(this);


        dialog=new ProgressDialog(this);

        Intent intent=getIntent();
        title=intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);
        init();

        mUploadButton.setOnClickListener(this);

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

        getBanners();

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
        getBanners();
    }


    private void getBanners(){
        mNotFoundLayout.setVisibility(View.GONE);
        if (networkConnection.isNetworkConnected(this)) {
            //get client and call object for request

            Call<BannerGetObj> call = client.fetch_banner();
            call.enqueue(new Callback<BannerGetObj>() {
                @Override
                public void onResponse(Call<BannerGetObj> call, Response<BannerGetObj> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed");
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

                    bannerList = response.body().getData();


                    int count = bannerList.size();


                    if (count == 0) {
                        showMessage("Empty Banner");
                        mNotFoundLayout.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);

                    } else {
                        list();
                    }

                }


                @Override
                public void onFailure(Call<BannerGetObj> call, Throwable t) {
                    showMessage("Fetch failed, check your internet ");
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
        bannerAdapter = new BannerAdapter(this, bannerList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        mRecyclerBanner.setLayoutManager(linearLayoutManager);
        mRecyclerBanner.setItemAnimator(new DefaultItemAnimator());
        mRecyclerBanner.setAdapter(bannerAdapter);
    }

    private void showMessage(String s) {
        Snackbar.make(mManageBannerLayout, s, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.upload_button:
                chooseImageFile();
                break;

        }

    }

    

    private void chooseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            showMessage("No image is selected, try again");
            return;
        }


        if (networkConnection.isNetworkConnected(this)) {
            Random random=new Random();
            String rand= String.valueOf(random.nextInt());
            if (requestCode == 1) {
                
                banner_img_uri = data.getData();

                try {
                    if (banner_img_uri != null) {
                        String name = "banner"+rand;
                        if (name.equals("")) {
                            showMessage("Try again");

                        } else {
                            
                            String imageId = MediaManager.get().upload(Uri.parse(banner_img_uri.toString()))
                                    .option("public_id", "Banners/advert_" + name)
                                    .unsigned("kfaatqjb").callback(new UploadCallback() {
                                        @Override
                                        public void onStart(String requestId) {
                                            // your code here
                                            mUploadButton.setVisibility(View.GONE);
                                            mProgressbar.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onProgress(String requestId, long bytes, long totalBytes) {
                                            // example code starts here
                                            Double progress = (double) bytes / totalBytes;
                                            // post progress to app UI (e.g. progress bar, notification)
                                            // example code ends here
                                            mProgressbar.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onSuccess(String requestId, Map resultData) {
                                            // your code here


                                            Log.i("ImageRequestId ", requestId);
                                            Log.i("ImageUrl ", String.valueOf(resultData.get("url")));

                                            banner_img_url = String.valueOf(resultData.get("url"));
                                            addBanner(banner_img_url);

                                        }

                                        @Override
                                        public void onError(String requestId, ErrorInfo error) {
                                            // your code here
                                            showMessage("Error: " + error.toString());
                                            Log.i("Error: ", error.toString());

                                            mUploadButton.setVisibility(View.VISIBLE);
                                            mProgressbar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onReschedule(String requestId, ErrorInfo error) {
                                            // your code here
                                        }
                                    })
                                    .dispatch();

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Please Check your Image");

                }

            }
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    


    private void addBanner(String banner_url){

        mUploadButton.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);

        Addbanner addbanner=new Addbanner(banner_url);

        Call<Message> call = client.add_banner(addbanner);

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
                            mUploadButton.setVisibility(View.VISIBLE);
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
                        mUploadButton.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        return;
                    }
                    mUploadButton.setVisibility(View.VISIBLE);
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
                    showMessage("Add Failed: " + e.getMessage());
                    mUploadButton.setVisibility(View.VISIBLE);
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
                mUploadButton.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }



    @Override
    public void onDeleteClicked(int position) {

            dialogMessage();

            DeleteBanner deleteBanner=new DeleteBanner(position);

            Call<Message> call = client.delete_banner(deleteBanner);

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


    private void dialogMessage() {
        dialog.setMessage("Deleting Banner... please wait");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



}
