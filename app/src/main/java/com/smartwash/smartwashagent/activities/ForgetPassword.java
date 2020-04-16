package com.smartwash.smartwashagent.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwash.smartwashagent.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForgetPassword extends AppCompatActivity implements View.OnClickListener{

    /** ButterKnife Code **/
    @BindView(R.id.layout_forget_pass)
    FrameLayout mLayoutForgetPass;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout mInputLayoutEmail;
    @BindView(R.id.email_editxt)
    EditText mEmailEditxt;
    @BindView(R.id.inputLayoutConfirmPass)
    TextInputLayout inputLayoutConfirmPass;
    @BindView(R.id.confirm_password_editxt)
    EditText confirm_password_editxt;

    @BindView(R.id.inputLayoutToken)
    TextInputLayout mInputLayoutToken;
    @BindView(R.id.token_editxt)
    EditText mTokenEditxt;
    @BindView(R.id.inputLayoutNewPassword)
    TextInputLayout mInputLayoutNewPassword;
    @BindView(R.id.new_password_editxt)
    EditText mNewPasswordEditxt;
    @BindView(R.id.initiate_btn)
    Button mInitiateBtn;
    @BindView(R.id.reset_btn)
    Button mResetBtn;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.reset_pass)
    TextView mResetPass;
    /** ButterKnife Code **/

    UserPreferences userPreferences;


    String user_email="",name="";
    NetworkConnection networkConnection=new NetworkConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        mResetPass.setPaintFlags(mResetPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mResetPass.setText("Click Here to Reset");



        userPreferences = new UserPreferences(this);

        Intent intent=getIntent();
        user_email=intent.getStringExtra(Constant.EMAIL);
        setUp();

        mInitiateBtn.setOnClickListener(this);
        mResetPass.setOnClickListener(this);
        mResetBtn.setOnClickListener(this);

    }

    private void setUp(){
        mEmailEditxt.setText(user_email);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.initiate_btn:
                ValidateForm();
                break;


            case R.id.reset_pass:
                mInputLayoutToken.setVisibility(View.VISIBLE);
                mInputLayoutNewPassword.setVisibility(View.VISIBLE);
                inputLayoutConfirmPass.setVisibility(View.VISIBLE);
                mInitiateBtn.setVisibility(View.GONE);
                mResetBtn.setVisibility(View.VISIBLE);
                mResetPass.setVisibility(View.GONE);
                break;

            case R.id.reset_btn:
                ValidateFormResetPass();
                break;

        }

    }

    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mEmailEditxt.getText().toString().isEmpty()) {
                mInputLayoutEmail.setError("Email is required!");
                isValid = false;
            } else {
                mInputLayoutEmail.setErrorEnabled(false);
            }

            if (isValid) {

                //Post Request to Api
               // sendDataForRequest();
              }

          return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void ValidateFormResetPass() {

        if (networkConnection.isNetworkConnected(this)) {
            boolean isValid = true;

            if (mEmailEditxt.getText().toString().isEmpty()) {
                mInputLayoutEmail.setError("Email is required!");
                isValid = false;
            }else if (mTokenEditxt.getText().toString().isEmpty()) {
                mInputLayoutToken.setError("Token is required, check your mail");
                isValid = false;
            }else if (mNewPasswordEditxt.getText().toString().isEmpty()) {
                mInputLayoutNewPassword.setError("Enter your New Password");
                isValid = false;
            } else if (confirm_password_editxt.getText().toString().isEmpty()) {
                inputLayoutConfirmPass.setError("Confirm your New Password");
                isValid = false;
            }else {
                mInputLayoutEmail.setErrorEnabled(false);
                mInputLayoutToken.setErrorEnabled(false);
                mInputLayoutNewPassword.setErrorEnabled(false);
                inputLayoutConfirmPass.setErrorEnabled(false);
            }
            if(!mNewPasswordEditxt.getText().toString().equals(confirm_password_editxt.getText().toString().isEmpty())){
                inputLayoutConfirmPass.setError("Password not Matched");
                isValid = false;
            }else {
                inputLayoutConfirmPass.setErrorEnabled(false);
            }

            if (isValid) {

                //Post Request to Api
              //  sendDataForReset();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }

   /* private void sendDataForReset(){
        mResetBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        UserNewPassPost userNewPassPost=new UserNewPassPost(mEmailEditxt.getText().toString(),mTokenEditxt.getText().toString(),
                mNewPasswordEditxt.getText().toString());

        UserHNew userHNew=new UserHNew(userNewPassPost);

        sentPasswordReset(userHNew);
    }




    private void sendDataForRequest(){
        mInitiateBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);

        UserEmail userEmail=new UserEmail(mEmailEditxt.getText().toString());

        UserHead userHead=new UserHead(userEmail);

        sentPasswordRequest(userHead);
    }

    private  void sentPasswordRequest( UserHead userHead){


            //get client and call object for request
            ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

            Call<UserHName> call = client.initiate_forget_pass(userHead);

            call.enqueue(new Callback<UserHName>() {
                @Override
                public void onResponse(Call<UserHName> call, Response<UserHName> response) {
                    try {
                        if (!response.isSuccessful()) {

                            try {
                                APIError apiError = ErrorUtils.parseError(response);

                                showMessage("Invalid Entry: " + apiError.getErrors());
                                Log.i("Invalid EntryK", apiError.getErrors().toString());
                                Log.i("Invalid Entry", response.errorBody().toString());

                            } catch (Exception e) {
                                Log.i("InvalidEntry", e.getMessage());
                                showMessage("Invalid Entry");
                                mInitiateBtn.setVisibility(View.VISIBLE);
                                mAvi1.setVisibility(View.GONE);

                            }
                            mInitiateBtn.setVisibility(View.VISIBLE);
                            mAvi1.setVisibility(View.GONE);
                            return;
                        }


                        name=response.body().getUser_name().getName();

                        alertUser();

                        mInitiateBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        mResetPass.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        showMessage("Login Failed: " + e.getMessage());
                        mInitiateBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        Log.i("Login Failed", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserHName> call, Throwable t) {
                    showMessage("Login Failed " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                    mInitiateBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                }
            });



    }



    private  void sentPasswordReset( UserHNew userHNew){

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<ResponseBody> call = client.reset_pass(userHNew);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
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
                        mResetBtn.setVisibility(View.VISIBLE);
                        mAvi1.setVisibility(View.GONE);
                        return;
                    }




                    showMessage("Successfully changed password");
                    startActivity(new Intent(ForgetPassword.this,SignIn.class));


                } catch (Exception e) {
                    showMessage("Login Failed: " + e.getMessage());
                    mResetBtn.setVisibility(View.VISIBLE);
                    mAvi1.setVisibility(View.GONE);
                    Log.i("Login Failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showMessage("Login Failed " + t.getMessage());
                Log.i("GEtError", t.getMessage());
                mResetBtn.setVisibility(View.VISIBLE);
                mAvi1.setVisibility(View.GONE);
            }
        });



    }*/

  /*  private  void alertUser(){

        new AlertDialog.Builder(this)
                .setTitle("Check your mail")
                .setMessage("Hi "+name+"\n"+"You can check your mail box to get the token to set a New Password")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
*/
    private void showMessage(String s) {
        Snackbar.make(mLayoutForgetPass, s, Snackbar.LENGTH_LONG).show();
    }
}
