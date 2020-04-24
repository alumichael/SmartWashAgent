package com.smartwash.smartwashagent.retrofit_interface;



import com.smartwash.smartwashagent.Model.Banner.Addbanner;
import com.smartwash.smartwashagent.Model.Banner.BannerGetObj;
import com.smartwash.smartwashagent.Model.Banner.DeleteBanner;
import com.smartwash.smartwashagent.Model.Category.AddCategory;
import com.smartwash.smartwashagent.Model.Category.CategoryGetObj;
import com.smartwash.smartwashagent.Model.Category.DeleteCategory;
import com.smartwash.smartwashagent.Model.Category.UpdateCategory;
import com.smartwash.smartwashagent.Model.ClothList.AddCloth;
import com.smartwash.smartwashagent.Model.ClothList.ClothGetObj;
import com.smartwash.smartwashagent.Model.ClothList.DeleteCloth;
import com.smartwash.smartwashagent.Model.ClothList.UpdateCloth;
import com.smartwash.smartwashagent.Model.LoginModel.AdminGetObj;
import com.smartwash.smartwashagent.Model.LoginModel.AdminPostData;
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

    @POST("api/Auth/adminregister.php")
    Call<Message> register(@Body RegisterUserPost regPostData);

    @POST("api/category/addCategory.php")
    Call<Message> add_service(@Body AddCategory addCategory);

    @POST("api/banner/addBanner.php")
    Call<Message> add_banner(@Body Addbanner addbanner);

    @POST("api/cloth/addCloth.php")
    Call<Message> add_cloth(@Body AddCloth a);

    @POST("api/category/updateCategory.php")
    Call<Message> update_service(@Body UpdateCategory updateCategory);

    @POST("api/cloth/updateCloth.php")
    Call<Message> update_cloth(@Body UpdateCloth updateCloth);

    @POST("api/auth/login.php")
    Call<AdminGetObj> login(@Body AdminPostData adminPostData);

    @POST("api/Auth/adminLogin.php")
    Call<AdminGetObj> adminlogin(@Body AdminPostData adminPostData);

    @POST("api/category/deleteCategory.php")
    Call<Message> delete_service(@Body DeleteCategory deleteCategory);

    @POST("api/banner/deleteBanner.php")
    Call<Message> delete_banner(@Body DeleteBanner deleteBanner);

    @POST("api/cloth/deleteCloth.php")
    Call<Message> delete_cloth(@Body DeleteCloth deleteCloth);

    @POST("api/Auth/updateprofile.php")
    Call<ResponseBody> update_profile(@Body Profile_updatePost profile_updatePost);

    @POST("api/wallet/create.php")
    Call<ResponseBody> create_wallet(@Body createWallet createWallet);

    @GET("api/category/fetchCategories.php")
    Call<CategoryGetObj> fetch_service_cat();

    @GET("api/cloth/fetchCloth.php")
    Call<ClothGetObj> fetch_cloths();

    @GET("api/banner/fetchBanners.php")
    Call<BannerGetObj> fetch_banner();


    @POST("api/wallet/fetchwallet.php")
    Call<fetchWallet> fetch_wallet(@Body OnlyIDRequest onlyIDRequest);


    @POST("api/transaction/fetchtransactions.php")
    Call<transactHead> fetch_transaction(@Body OnlyIDRequest onlyIDRequest);


    @POST("api/order/fetchuserorders.php")
    Call<OrderStatusHead> fetch_order_status(@Body OnlyIDRequest userId);

    @GET("api/order/fetchallorders.php")
    Call<OrderStatusHead> fetch_all_order_status();


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
