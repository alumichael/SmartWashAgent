package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartwash.smartwashagent.BuildConfig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.Profile.Profile_updatePost;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity{


    @BindView(R.id.serviceform_toolbar)
    Toolbar toolBar;

    @BindView(R.id.profileLayout)
    LinearLayout mProfileLay;
    @BindView(R.id.relative_layout_photo)
    RelativeLayout mRelativeLayoutPhoto;
    @BindView(R.id.profile_photo)
    CircleImageView mProfilePhoto;
    @BindView(R.id.edit_prof)
    ImageView mEditProf;
    @BindView(R.id.fullname_txt)
    TextView mFullNameTxt;
    @BindView(R.id.progressBar_profile)
    ProgressBar mProgressBarProfile;
    @BindView(R.id.profile_data_layout)
    ScrollView mProfileDataLayout;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.phone_num)
    TextView mPhoneNum;
    @BindView(R.id.role_txt)
    TextView mRole;
    @BindView(R.id.address_txt)
    TextView mAddressTxt;

    @BindView(R.id.edit_layout)
    ScrollView mEditLayout;

    @BindView(R.id.inputLayoutFullName)
    TextInputLayout mInputLayoutFullNameP;
    @BindView(R.id.full_editxt)
    EditText full_editxt;


    @BindView(R.id.inputLayoutAddr)
    TextInputLayout mInputLayoutAddress;
    @BindView(R.id.address_editxt)
    EditText address_editxt;

    @BindView(R.id.inputLayoutPhone_NumP)
    TextInputLayout mInputLayoutPhoneNumP;
    @BindView(R.id.phone_num_editxt)
    EditText mPhoneNumEditxt;
    @BindView(R.id.update_btn)
    Button mUpdateBtn;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;


    String address,fullname,phone_num,cameraFilePath;

    int PICK_IMAGE_PASSPORT = 1;
    int CAM_IMAGE_PASSPORT = 2;
    NetworkConnection networkConnection=new NetworkConnection();

    Uri profile_photo_img_uri;
    String personal_img_url;
    UserPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);
        applyToolbarChildren("Admin Profile");
        getUserProfile();

        
    }


    private void applyToolbarChildren(String title) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }




    @OnClick(R.id.edit_prof)
    public void showEditProfile() {

        editProfile();

    }

    private void chooseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_PASSPORT);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }



    private void chooseIdImage_camera() {

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile()));
            startActivityForResult(intent, CAM_IMAGE_PASSPORT);
        } catch (IOException ex) {
            ex.printStackTrace();
            //   showMessage("Invalid Entry");
            Log.i("Invalid_Cam_Entry",ex.getMessage());
        }
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
                profile_photo_img_uri = data.getData();

                try {
                    if (profile_photo_img_uri != null) {
                        String name = "profile_photo"+rand;
                        if (name.equals("")) {
                            showMessage("Try again");

                        } else {
                            mProfilePhoto.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), profile_photo_img_uri));


                            String imageId = MediaManager.get().upload(Uri.parse(profile_photo_img_uri.toString()))
                                    .option("public_id", "agent_registration/agent_files/passport" + name)
                                    .unsigned("z2uab1xl").callback(new UploadCallback() {
                                        @Override
                                        public void onStart(String requestId) {
                                            // your code here
                                            mUpdateBtn.setVisibility(View.GONE);
                                            mAvi1.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onProgress(String requestId, long bytes, long totalBytes) {
                                            // example code starts here
                                            Double progress = (double) bytes / totalBytes;
                                            // post progress to app UI (e.g. progress bar, notification)
                                            // example code ends here
                                            mAvi1.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onSuccess(String requestId, Map resultData) {
                                            // your code here


                                            Log.i("ImageRequestId ", requestId);
                                            Log.i("ImageUrl ", String.valueOf(resultData.get("url")));

                                            personal_img_url = String.valueOf(resultData.get("url"));
                                            //updateImage(personal_img_url);

                                        }

                                        @Override
                                        public void onError(String requestId, ErrorInfo error) {
                                            // your code here
                                            showMessage("Error: " + error.toString());
                                            Log.i("Error: ", error.toString());

                                            mUpdateBtn.setVisibility(View.VISIBLE);
                                            mAvi1.setVisibility(View.GONE);
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

            }else if(requestCode == 2){
                profile_photo_img_uri = Uri.parse(cameraFilePath);

                try {
                    if (profile_photo_img_uri != null) {
                        String name = "profile_photo"+rand;
                        if (name.equals("")) {
                            showMessage("Try again");

                        } else {
                            mProfilePhoto.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), profile_photo_img_uri));


                            String imageId = MediaManager.get().upload(Uri.parse(profile_photo_img_uri.toString()))
                                    .option("public_id", "user_registration/profile_photos/user_passport" + name)
                                    .unsigned("z2uab1xl").callback(new UploadCallback() {
                                        @Override
                                        public void onStart(String requestId) {
                                            // your code here
                                            mUpdateBtn.setVisibility(View.GONE);
                                            mAvi1.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onProgress(String requestId, long bytes, long totalBytes) {
                                            // example code starts here
                                            Double progress = (double) bytes / totalBytes;
                                            // post progress to app UI (e.g. progress bar, notification)
                                            // example code ends here
                                            mAvi1.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onSuccess(String requestId, Map resultData) {
                                            // your code here


                                            Log.i("ImageRequestId ", requestId);
                                            Log.i("ImageUrl ", String.valueOf(resultData.get("url")));

                                            personal_img_url = String.valueOf(resultData.get("url"));
                                            //   updateImage(personal_img_url);

                                        }

                                        @Override
                                        public void onError(String requestId, ErrorInfo error) {
                                            // your code here
                                            showMessage("Error: " + error.toString());
                                            Log.i("Error: ", error.toString());

                                            mUpdateBtn.setVisibility(View.VISIBLE);
                                            mAvi1.setVisibility(View.GONE);
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

   /* private void updateImage(String url) {


        ProfileImageUser profileImageUser = new ProfileImageUser(url);
        ProfileImagePostHead profileImagePostHead = new ProfileImagePostHead(profileImageUser);

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<ProfileGetHead> call = client.change_profile_image("Token " + userPreferences.getUserToken(), profileImagePostHead);

        call.enqueue(new Callback<ProfileGetHead>() {
            @Override
            public void onResponse(Call<ProfileGetHead> call, Response<ProfileGetHead> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));


                if (response.code() == 400) {
                    showMessage("Check your internet connection");
                    mAvi1.setVisibility(View.GONE);
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    return;
                } else if (response.code() == 429) {
                    showMessage("Too many requests on database");
                    mAvi1.setVisibility(View.GONE);
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    return;
                } else if (response.code() == 500) {
                    showMessage("Server Error");
                    mAvi1.setVisibility(View.GONE);
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    return;
                } else if (response.code() == 401) {
                    showMessage("Unauthorized access, please try login again");
                    mAvi1.setVisibility(View.GONE);
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: " + apiError.getErrors());
                            Log.i("Invalid EntryK", apiError.getErrors().toString());
                            Log.i("Invalid Entry", response.errorBody().toString());
                            mAvi1.setVisibility(View.GONE);
                            mUpdateBtn.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            Log.i("ResponseError", response.errorBody().string());
                            showMessage("Failed to Register" + e.getMessage());
                            mAvi1.setVisibility(View.GONE);
                            mUpdateBtn.setVisibility(View.VISIBLE);

                        }
                        mAvi1.setVisibility(View.GONE);
                        mUpdateBtn.setVisibility(View.VISIBLE);
                        return;
                    }

                    personal_img_url = response.body().getUser().getImage();
                    userPreferences.setAgentProfileImg(personal_img_url);

                    if(personal_img_url==null) {
                        Glide.with(this).load(userPreferences.getProfileImg()).apply(new RequestOptions().fitCenter().circleCrop()).into(mProfilePhoto);
                    }else{
                        Glide.with(this).load(personal_img_url).apply(new RequestOptions().fitCenter().circleCrop()).into(mProfilePhoto);

                    }

                    showMessage("Image Uploaded Successfully");
                    mAvi1.setVisibility(View.GONE);
                    mUpdateBtn.setVisibility(View.VISIBLE);


                } catch (Exception e) {
                    showMessage("Upload Error: " + e.getMessage());
                    Log.i("Upload", e.getMessage());
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ProfileGetHead> call, Throwable t) {
                showMessage("Upload Failed, Try Again: " + t.getMessage());
                mUpdateBtn.setVisibility(View.VISIBLE);
                mAvi1.setVisibility(View.GONE);
                Log.i("GetError", t.getMessage());
            }
        });


    }*/

    private void editProfile() {
        mProfileDataLayout.setVisibility(View.GONE);
        mEditProf.setVisibility(View.GONE);
        mProfilePhoto.setClickable(true);
        mEditLayout.setVisibility(View.VISIBLE);


        mPhoneNumEditxt.setText(userPreferences.getPhone());
        full_editxt.setText(userPreferences.getFullName());
        address_editxt.setText(userPreferences.getUserAddr1());



        mProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cam_gallary();
            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Request for Post Request for Profile Update
                validateUserInputs();
            }
        });
    }

    private  void cam_gallary(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Mode of Entry");
// add a list
        String[] entry = {"Camera", "Gallery"};
        builder.setItems(entry, (dialog, option) -> {
            switch (option) {
                case 0:
                    // direct entry
                    chooseIdImage_camera();
                    dialog.dismiss();
                    break;

                case 1: // export

                    chooseImageFile();
                    dialog.dismiss();

                    break;

            }
        });
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void validateUserInputs() {

        boolean isValid = true;

        if (full_editxt.getText().toString().trim().isEmpty()) {
            mInputLayoutFullNameP.setError("Full Name is Required !");
            isValid = false;
        }else if (mPhoneNumEditxt.getText().toString().trim().isEmpty()) {
            mInputLayoutPhoneNumP.setError("Phone number is Required!");
            isValid = false;
        } else if (address_editxt.getText().toString().trim().isEmpty()) {
            mInputLayoutAddress.setError("Address is Required!");
            isValid = false;
        }else if (mPhoneNumEditxt.getText().toString().trim().length() != 11) {
            mInputLayoutPhoneNumP.setError("Your Phone number must be 11 in length");
            isValid = false;
        }else {
            mInputLayoutFullNameP.setErrorEnabled(false);
            mInputLayoutAddress.setErrorEnabled(false);
            mInputLayoutPhoneNumP.setErrorEnabled(false);

        }

        if (isValid) {
            if (networkConnection.isNetworkConnected(this)) {
                upDateProfile();
            }else {
                showMessage("No Internet Connection");
            }
        }


    }

    private void upDateProfile(){
        mUpdateBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        Profile_updatePost profile_updatePost = new Profile_updatePost(full_editxt.getText().toString(), mPhoneNumEditxt.getText().toString(),
                userPreferences.getCustomerId());


        updateUserData(profile_updatePost);


    }


    private void updateUserData(Profile_updatePost profile_updatePost){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<ResponseBody> call=client.update_profile(profile_updatePost);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Failed Entry: " + apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            mAvi1.setVisibility(View.GONE);
                            mUpdateBtn.setVisibility(View.VISIBLE);

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showMessage("Failed to Update: " + e.getMessage());
                            mUpdateBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);

                        }
                        mUpdateBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        return;
                    }

                    fullname=full_editxt.getText().toString();
                    address=address_editxt.getText().toString();
                    phone_num=mPhoneNumEditxt.getText().toString();

                    userPreferences.setFullName(fullname);
                    userPreferences.setPhone(phone_num);
                    userPreferences.setUserAddr1(address);


                    mUpdateBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);

                    showMessage("Account Updated Successfully");
                    getUserProfile();

                    mProfilePhoto.setClickable(false);
                    mEditLayout.setVisibility(View.GONE);

                    mProfileDataLayout.setVisibility(View.VISIBLE);
                    mEditProf.setVisibility(View.VISIBLE);



                }catch (Exception e){
                    showMessage("Submission Error: " + e.getMessage());
                    Log.i("Submission", e.getMessage());
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showMessage("Update Failed " + t.getMessage());
                mUpdateBtn.setVisibility(View.VISIBLE);
                mAvi1.setVisibility(View.GONE);
                Log.i("GEtError",t.getMessage());
            }
        });

    }


    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void getUserProfile() {
        //Getting profile from Pref
        Log.i("fullname",userPreferences.getFullName());

        if(userPreferences.getFullName()==null){
            mFullNameTxt.setText("");
        }else if(userPreferences.getFullName().equals("null")){
            mFullNameTxt.setText("");
        }else{
            mFullNameTxt.setText(userPreferences.getFullName());
        }

        mEmail.setText("Email: "+userPreferences.getUserEmail());
        mPhoneNum.setText("Phone No: "+userPreferences.getPhone());
        mAddressTxt.setText("Address: "+userPreferences.getUserAddr1());
        mRole.setText("Role: "+userPreferences.getRole());

        // mProgressBarProfile.setVisibility(View.VISIBLE);

       /* if(personal_img_url==null) {
            Glide.with(this).load(userPreferences.getAgentProfileImg()).apply(new RequestOptions().fitCenter().circleCrop()).into(mProfilePhoto);
        }else{
            Glide.with(this).load(personal_img_url).apply(new RequestOptions().fitCenter().circleCrop()).into(mProfilePhoto);

        }*/

        //mProgressBarProfile.setVisibility(View.GONE);
    }






    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        this.finish();
        super.onBackPressed();
    }




}
