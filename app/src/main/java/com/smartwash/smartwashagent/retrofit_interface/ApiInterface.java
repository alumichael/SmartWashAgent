package com.smartwash.smartwashagent.retrofit_interface;



import com.smartwash.smartwashagent.Model.Category.CategoryGetObj;
import com.smartwash.smartwashagent.Model.ClothList.ClothGetObj;
import com.smartwash.smartwashagent.Model.LoginModel.UserGetObj;
import com.smartwash.smartwashagent.Model.LoginModel.UserPostData;
import com.smartwash.smartwashagent.Model.Message;
import com.smartwash.smartwashagent.Model.OnlyIDAmountRequest;
import com.smartwash.smartwashagent.Model.OnlyIDRequest;
import com.smartwash.smartwashagent.Model.OrderPost.OrderHead;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderStatusHead;
import com.smartwash.smartwashagent.Model.Profile.Profile_updatePost;
import com.smartwash.smartwashagent.Model.Register.RegisterUserPost;
import com.smartwash.smartwashagent.Model.Transaction.VerifyTransact;
import com.smartwash.smartwashagent.Model.Transaction.newTransact;
import com.smartwash.smartwashagent.Model.Transaction.transactHead;
import com.smartwash.smartwashagent.Model.Wallet.createWallet;
import com.smartwash.smartwashagent.Model.Wallet.fetchWallet;
import com.smartwash.smartwashagent.Model.updateOrder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("api/auth/register.php")
    Call<Message> register(@Body RegisterUserPost regPostData);

    @POST("api/auth/login.php")
    Call<UserGetObj> login(@Body UserPostData userPostData);

    @POST("api/Auth/updateprofile.php")
    Call<ResponseBody> update_profile(@Body Profile_updatePost profile_updatePost);

    @POST("api/wallet/create.php")
    Call<ResponseBody> create_wallet(@Body createWallet createWallet);

    @GET("api/category/fetchCategories.php")
    Call<CategoryGetObj> fetch_service_cat();

    @GET("api/cloth/fetchCloth.php")
    Call<ClothGetObj> fetch_cloths();


    @POST("api/wallet/fetchwallet.php")
    Call<fetchWallet> fetch_wallet(@Body OnlyIDRequest onlyIDRequest);


    @POST("api/transaction/fetchtransactions.php")
    Call<transactHead> fetch_transaction(@Body OnlyIDRequest onlyIDRequest);


    @POST("api/order/fetchuserorders.php")
    Call<OrderStatusHead> fetch_order_status(@Body OnlyIDRequest userId);


    @POST("api/wallet/debitwallet.php")
    Call<Message> debit_wallet(@Body OnlyIDAmountRequest onlyIDAmountRequest);


    @POST("api/wallet/creditwallet.php")
    Call<Message> credit_wallet(@Body OnlyIDAmountRequest onlyIDAmountRequest);



    @POST("api/transaction/newtransaction.php")
    Call<Message> create_transact(@Body newTransact transact);


    @POST("api/transaction/verifytransaction.php")
    Call<Message> verify_transact(@Body VerifyTransact verifyTransact);

    @POST("api/order/neworder.php")
    Call<Message> new_order(@Body OrderHead orderHead);



    @POST("api/order/updateorderstatus.php")
    Call<Message> updateOrderby_customer(@Body updateOrder order);





}
