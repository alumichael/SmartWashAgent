package com.smartwash.smartwashagent.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class UserPreferences {
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private Context _context;

    @SuppressLint({"CommitPrefEdits"})
    public UserPreferences(Context _context) {
        this._context = _context;
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, Constant.PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

   

    public void setUserLogged(boolean usLg) {
        editor.putBoolean(Constant.IS_USER_LOGGED, usLg);
        editor.apply();
    }

    public boolean isUserLogged() {
        return sharedPreferences.getBoolean(Constant.IS_USER_LOGGED, false);
    }

    public void setFacebookToken(String facebookToken) {
        editor.putString(Constant.FACEBOOK_TOKEN, facebookToken);
        editor.apply();
    }

    public String getColorAtrribute() {
        return sharedPreferences.getString(Constant.COLOR_ATTR, "");
    }

    public void setColorAtrribute(String color) {
        editor.putString(Constant.COLOR_ATTR, color);
        editor.apply();
    }

    public String getTotalAmount() {
        return sharedPreferences.getString(Constant.TOTAL_AMT, "");
    }

    public void setTotalAmount(String totalAmount) {
        editor.putString(Constant.TOTAL_AMT, totalAmount);
        editor.apply();
    }

    public String getSizeAttribute() {
        return sharedPreferences.getString(Constant.SIZE_ATTR, "");
    }

    public void setSizeAttribute(String size) {
        editor.putString(Constant.SIZE_ATTR, size);
        editor.apply();
    }


    public String getFacebookToken() {
        return sharedPreferences.getString(Constant.FACEBOOK_TOKEN, "");
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(Constant.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(Constant.IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSentSuccess(boolean isSentSuccess) {
        editor.putBoolean(Constant.IS_SENT_SUCCESS, isSentSuccess);
        editor.apply();
    }

    public boolean isSentSuccess() {
        return sharedPreferences.getBoolean(Constant.IS_SENT_SUCCESS, false);
    }






    public void setCustomerId(String customer_id) {
        editor.putString(Constant.CUSTOMER_ID, customer_id);
        editor.apply();
    }

    public String getCustomerId() {
        return sharedPreferences.getString(Constant.CUSTOMER_ID, "");
    }

    public void setFullName(String name) {
        editor.putString(Constant.NAME, name);
        editor.apply();
    }

    public String getFullName() {
        return sharedPreferences.getString(Constant.NAME, "");
    }

    public void setUserEmail(String email) {
        editor.putString(Constant.EMAIL, email);
        editor.apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(Constant.EMAIL, "");
    }

    public void setUserAddr1(String addr1) {
        editor.putString(Constant.ADDR1, addr1);
        editor.apply();
    }

    public String getUserAddr1() {
        return sharedPreferences.getString("address_1", "");
    }


    public void setUserFirstname(String firstname) {
        editor.putString(Constant.FIRSTNAME, firstname);
        editor.apply();
    }

    public String getUserFirstname() {
        return sharedPreferences.getString(Constant.FIRSTNAME, "");
    }

    public void setUserLastname(String lastname) {
        editor.putString(Constant.LASTNAME, lastname);
        editor.apply();
    }

    public String getUserLastname() {
        return sharedPreferences.getString(Constant.LASTNAME, "");
    }

    public void setUserState(String userState) {
        editor.putString(Constant.USER_STATE, userState);
        editor.apply();
    }

    public String getUserState() {
        return sharedPreferences.getString(Constant.USER_STATE, "");
    }


    public void setUserAddr2(String addr2) {
        editor.putString(Constant.ADDR2, addr2);
        editor.apply();
    }

    public String getUserAddr2() {
        return sharedPreferences.getString(Constant.ADDR2, "");
    }


    public void setUserCity(String city) {
        editor.putString(Constant.CITY, city);
        editor.apply();
    }

    public String getUserCity() {
        return sharedPreferences.getString(Constant.CITY, "");
    }



    public void setUserRegion(String region) {
        editor.putString(Constant.REGION, region);
        editor.apply();
    }

    public String getUserRegion() {
        return sharedPreferences.getString(Constant.REGION, "");
    }

    public void setUserPostalCode(String postalCode) {
        editor.putString(Constant.POSTAL_CODE, postalCode);
        editor.apply();
    }


    public String getUserPostalCode() {
        return sharedPreferences.getString(Constant.POSTAL_CODE, "");
    }


    public void setUserCountry(String country) {
        editor.putString(Constant.COUNTRY, country);
        editor.apply();
    }

    public String getUserCountry() {
        return sharedPreferences.getString("country", "");
    }


    public void setRole(String role) {
        editor.putString(Constant.ROLE, role);
        editor.apply();
    }

    public String getRole() {
        return sharedPreferences.getString(Constant.ROLE, "");
    }






    public void setWalletBalance(String role) {
        editor.putString(Constant.WALLET_BALANCE, role);
        editor.apply();
    }

    public String getWalletBalance() {
        return sharedPreferences.getString(Constant.WALLET_BALANCE, "");
    }




    public void setID(String id) {
        editor.putString(Constant.ID, id);
        editor.apply();
    }

    public String getID() {
        return sharedPreferences.getString(Constant.ID, "");
    }

    public void setPhone(String phone) {
        editor.putString(Constant.PHONE, phone);
        editor.apply();
    }

    public String getPhone() {
        return sharedPreferences.getString(Constant.PHONE, "");
    }

    public void setUserShippingRegionId(int shippingRegionId) {
        editor.putInt(Constant.SHIPPING_REGION_ID, shippingRegionId);
        editor.apply();
    }

    public Integer getUserShippingRegionId() {
        return sharedPreferences.getInt(Constant.SHIPPING_REGION_ID, 0);
    }

    public void setUserDayPhone(String dayPhone) {
        editor.putString(Constant.DAY_PHONE, dayPhone);
        editor.apply();
    }

    public String getUserDayPhone() {
        return sharedPreferences.getString(Constant.DAY_PHONE, "");
    }

    public void setUserEvePhone(String evePhone) {
        editor.putString(Constant.EVE_PHONE, evePhone);
        editor.apply();
    }

    public String getUserEvePhone() {
        return sharedPreferences.getString(Constant.EVE_PHONE, "");
    }

    public void setUserMobPhone(String mobPhone) {
        editor.putString(Constant.MOB_PHONE, mobPhone);
        editor.apply();
    }

    public String getUserMobPhone() {
        return sharedPreferences.getString(Constant.MOB_PHONE, "");
    }

    public void setUserAccessToken(String accessToken) {
        editor.putString(Constant.ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getUserAccessToken() {
        return sharedPreferences.getString(Constant.ACCESS_TOKEN, "");
    }

    public void setUserExpiresIn(String expiresIn) {
        editor.putString(Constant.EXPIRE_IN, expiresIn);
        editor.apply();
    }

    public String getUserExpiresIn() {
        return sharedPreferences.getString(Constant.EXPIRE_IN, "");
    }


    public void setUserOrderId(String orderId) {
        editor.putString(Constant.ORDER_ID, orderId);
        editor.apply();
    }

    public String getUserOrderId() {
        return sharedPreferences.getString(Constant.ORDER_ID, "");
    }


    public String getUserLastOrder() {
        return sharedPreferences.getString(Constant.LAST_ORDER, "");
    }


    public void setUserLastOrder(String lastOrder) {
        editor.putString(Constant.LAST_ORDER, lastOrder);
        editor.apply();
    }

    public String getUserLastOrderId() {
        return sharedPreferences.getString(Constant.LAST_ORDER_ID, "");
    }


    public void setUserLastOrderId(String lastOderId) {
        editor.putString(Constant.LAST_ORDER_ID, lastOderId);
        editor.apply();
    }
    public void setUserOrderSize(int orderSize) {
        editor.putInt(Constant.ORDER_SIZE, orderSize);
        editor.apply();
    }

    public int getUserOrderSize() {
        return sharedPreferences.getInt(Constant.ORDER_SIZE, 0);
    }


    public void setUserCartId(String userCartId) {
        editor.putString(Constant.CART_ID, userCartId);
        editor.apply();
    }

    public String getUserCartId() {
        return sharedPreferences.getString(Constant.CART_ID,"");
    }


    public void setUserCartSize(int cartSize) {
        editor.putInt(Constant.CART_SIZE, cartSize);
        editor.apply();
    }

    public int getUserCartSize() {
        return sharedPreferences.getInt(Constant.CART_SIZE, 0);
    }


    public void setShippingCost(String shippingCost) {
        editor.putString(Constant.SHIPPING_COST, shippingCost);
        editor.apply();
    }

    public String getShippingCost() {
        return sharedPreferences.getString(Constant.SHIPPING_COST, "");
    }



    //Counting
    public void setCount(int count) {
        editor.putInt(Constant.COUNT, count);
        editor.apply();
    }

    public int getCount() {
        return sharedPreferences.getInt(Constant.COUNT, 0);
    }


    public void setTotalPicked(int total_count) {
        editor.putInt(Constant.TOTAL_COUNT, total_count);
        editor.apply();
    }

    public int getTotalPicked() {
        return sharedPreferences.getInt(Constant.TOTAL_COUNT, 0);
    }




    public void setName(String category) {
        editor.putString(Constant.CATE_NAME, category);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(Constant.CATE_NAME, "");
    }

    public void setDescription(String description) {
        editor.putString(Constant.CATE_DESC, description);
        editor.apply();
    }

    public String getDescription() {
        return sharedPreferences.getString(Constant.CATE_DESC, "");
    }



    public void setPrice(String categoryPrice) {
        editor.putString(Constant.CATE_PRICE, categoryPrice);
        editor.apply();
    }

    public String getPrice() {
        return sharedPreferences.getString(Constant.CATE_PRICE, "");
    }


    public void setId(String categoryPrice) {
        editor.putString(Constant.ID, categoryPrice);
        editor.apply();
    }

    public String getId() {
        return sharedPreferences.getString(Constant.ID, "");
    }


    public void setTotalPrice(int total_price) {
        editor.putInt(Constant.TOTAL_PRICE, total_price);
        editor.apply();
    }

    public int getTotalPrice() {
        return sharedPreferences.getInt(Constant.TOTAL_PRICE, 0);
    }


    public void setBannerUrl(String bannerUrl) {
        editor.putString(Constant.BANNER_URL, bannerUrl);
        editor.apply();
    }

    public String getBannerUrl() {
        return sharedPreferences.getString(Constant.BANNER_URL, "");
    }
   


}
