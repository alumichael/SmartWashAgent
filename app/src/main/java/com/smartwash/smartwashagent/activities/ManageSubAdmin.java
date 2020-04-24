package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.Model.Register.RegisterUserPost;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSubAdmin extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.manage_add_admin_layout)
    LinearLayout mLayoutAddAdmin;
    @BindView(R.id.inputLayoutFullName)
    TextInputLayout mInputLayoutFullName;
    @BindView(R.id.fullname_editxt)
    EditText mFullnameEditxt;
    @BindView(R.id.inputLayoutPhoneNum)
    TextInputLayout mInputLayoutPhoneNum;
    @BindView(R.id.phonenum_editxt)
    EditText mPhonenumEditxt;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout mInputLayoutEmail;
    @BindView(R.id.email_editxt)
    EditText mEmailEditxt;

    @BindView(R.id.inputLayoutAddress)
    TextInputLayout mInputLayoutAddress;
    @BindView(R.id.addr_editxt)
    EditText mAddrEditxt;


    @BindView(R.id.inputLayoutPassword)
    TextInputLayout mInputLayoutPassword;
    @BindView(R.id.password_editxt)
    EditText mPasswordEditxt;

    @BindView(R.id.inputLayoutConfirmPassword)
    TextInputLayout mInputLayoutConfirmPassword;
    @BindView(R.id.confirm_pass_editxt)
    EditText mConfirmPassEdittxt;

    @BindView(R.id.signup_btn)
    Button mSignupBtn;

    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;

    /** ButterKnife Code **/

    NetworkConnection networkConnection=new NetworkConnection();
    String message;

    String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subadmin);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.CARD_OPTION_TITLE);

        applyToolbar(title);

        mSignupBtn.setOnClickListener(this);


    }

    private void applyToolbar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signup_btn:
                ValidateForm();
                break;


        }

    }


    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;
            if (mFullnameEditxt.getText().toString().isEmpty()) {
                mInputLayoutFullName.setError("Full Name is required");
                isValid = false;
            } else {
                mInputLayoutFullName.setErrorEnabled(false);
            }

            if (mAddrEditxt.getText().toString().isEmpty()) {
                mInputLayoutAddress.setError("Address is required");
                isValid = false;
            } else {
                mInputLayoutAddress.setErrorEnabled(false);
            }

            if (mEmailEditxt.getText().toString().isEmpty()) {
                mInputLayoutEmail.setError("Email is required!");
                isValid = false;
            } else if (!isValidEmailAddress(mEmailEditxt.getText().toString())&&mInputLayoutEmail.isClickable()) {
                mInputLayoutEmail.setError("Valid Email is required!");
                isValid = false;
            } else {
                mInputLayoutEmail.setErrorEnabled(false);
            }
            if (mPasswordEditxt.getText().toString().isEmpty()&&mInputLayoutPassword.isClickable()) {
                mInputLayoutPassword.setError("Password is required!");
                isValid = false;
            } else if (mPasswordEditxt.getText().toString().trim().length()<6 && mInputLayoutPassword.isClickable()) {
                mInputLayoutPassword.setError("Your Password must not less than 6 character");
                isValid = false;
            } else {
                mInputLayoutPassword.setErrorEnabled(false);
            }
            if (mConfirmPassEdittxt.getText().toString().trim().length()<6) {
                mInputLayoutPassword.setError("Your Password must not less than 6 character");
                isValid = false;
            } else if (! mConfirmPassEdittxt.getText().toString().trim().equals( mPasswordEditxt.getText().toString().trim())) {
                mInputLayoutPassword.setError("Your Password does not match");
                isValid = false;
            } else {
                mInputLayoutConfirmPassword.setErrorEnabled(false);
            }

            if (mPhonenumEditxt.getText().toString().isEmpty()) {
                mInputLayoutPhoneNum.setError("Phone number is required");
                isValid = false;
            } else if (mPhonenumEditxt.getText().toString().trim().length() < 11&&mInputLayoutPhoneNum.isClickable()) {
                mInputLayoutPhoneNum.setError("Your Phone number must be 11 in length");
                isValid = false;
            } else {
                mInputLayoutPhoneNum.setErrorEnabled(false);
            }


            if (isValid) {

                //Post Request to Api
                sendData();


            }

            //
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        if (null != email) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                result = false;
            }
        }

        return result;
    }

    private void sendData(){
        mSignupBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        RegisterUserPost dataPart = new RegisterUserPost(mFullnameEditxt.getText().toString(),
                mEmailEditxt.getText().toString(),mPhonenumEditxt.getText().toString(),mAddrEditxt.getText().toString(),
                mPasswordEditxt.getText().toString(),"admin");


        sentNetworkRequest(dataPart);

    }

    private  void sentNetworkRequest(RegisterUserPost regPostData){

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<Message> call=client.register(regPostData);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    return;
                }


                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            mSignupBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            showMessage("Failed to Register: "+e.getMessage());
                            mSignupBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);

                        }
                        mSignupBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        return;
                    }

                    if(response.code()==200){

                        String status=response.body().getStatus();

                        if(status.equals("success")) {
                            showMessage("Successfully Registered Admin");
                            startActivity(new Intent(ManageSubAdmin.this,MainActivity.class));
                            finish();
                        }else {
                            message=response.body().getMessage();
                            showMessage(message);
                        }


                    }

                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);

                }catch (Exception e){
                    showMessage("Registration Error: " + e.getMessage());
                    mSignupBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if(message!=null){
                    showMessage(message);
                }else{
                    showMessage("Admin Registration Failed");
                }
                Log.i("GEtError",t.getMessage());
                mSignupBtn.setVisibility(View.VISIBLE);
                mAvi1.setVisibility(View.GONE);
            }
        });



    }




    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


}
