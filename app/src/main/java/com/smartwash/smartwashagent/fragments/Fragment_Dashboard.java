package com.smartwash.smartwashagent.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.smartwash.smartwashagent.GridSpacingItemDecoration;
import com.smartwash.smartwashagent.Model.Card;
import com.smartwash.smartwashagent.Model.Category.CategoryGetObj;
import com.smartwash.smartwashagent.Model.Errors.APIError;
import com.smartwash.smartwashagent.Model.Errors.ErrorUtils;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.NetworkConnection;
import com.smartwash.smartwashagent.activities.ProfileActivity;
import com.smartwash.smartwashagent.activities.UserPreferences;
import com.smartwash.smartwashagent.adapters.CardAdapter;
import com.smartwash.smartwashagent.retrofit_interface.ApiInterface;
import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Dashboard extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.dash_layout)
    FrameLayout dash_layout;

    @BindView(R.id.main_layout)
    ScrollView main_layout;

    @BindView(R.id.avi1)
    AVLoadingIndicatorView avi1;


    @BindView(R.id.admin_name)
    TextView mAdminName;
    @BindView(R.id.role)
    TextView mRole;
    @BindView(R.id.email_txt)
    TextView mEmailTxt;
    @BindView(R.id.open_profile)
    ImageView mOpenProfile;

    @BindView(R.id.fund_wallet_card)
    MaterialCardView fund_wallet_ServiceCard;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;



    UserPreferences userPreferences;
    Fragment fragment;




    String role;
    String username;
    String email;

    private CardAdapter cardAdapter;
    private List<Card> cardList;


    //List<History> policy_item;

    public Fragment_Dashboard() {
        // Required empty public constructor
    }

    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dashboard newInstance(String param1, String param2) {
        Fragment_Dashboard fragment = new Fragment_Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__dashboard, container, false);
        ButterKnife.bind(this, view);
        userPreferences = new UserPreferences(getActivity());
        username=userPreferences.getFullName();
        role=userPreferences.getRole();
        email=userPreferences.getUserEmail();

        mAdminName.setText(username);
        mRole.setText(role);
        mEmailTxt.setText(email);

        cardList = new ArrayList<>();

        if(email.equals("tayo@gmail.com")){
            insertSuperAdminElement();

        }else{
            insertAdminElement();
        }

        setAction();

        return view;
    }

    private void setAction() {
        mOpenProfile.setOnClickListener(this);

    }

    private void insertAdminElement() {
//        referencing drawable for the logo
        int[] icons = new int[]{

                R.drawable.ic_tracking,
                R.drawable.ic_fashion,
                R.drawable.ic_filter_b_and_w_black_24dp

        };



        Card m = new Card("Check Order", icons[0]);
        cardList.add(m);


        m = new Card("Manage Clothing", icons[1]);
        cardList.add(m);


        m = new Card("Manage Banner", icons[2]);
        cardList.add(m);



        cardAdapter = new CardAdapter(getContext(), cardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

    }

    private void insertSuperAdminElement() {
//        referencing drawable for the logo
        int[] icons = new int[]{
                R.drawable.ic_household,
                R.drawable.ic_tracking,
                R.drawable.ic_fashion,
                R.drawable.ic_person_add_black_24dp,
                R.drawable.ic_filter_b_and_w_black_24dp

        };

        Card m = new Card("Manage Service", icons[0]);
        cardList.add(m);

        m = new Card("Check Order", icons[1]);
        cardList.add(m);


        m = new Card("Manage Clothing", icons[2]);
        cardList.add(m);


        m = new Card("Manage Sub-Admin", icons[3]);
        cardList.add(m);

        m = new Card("Manage Banner", icons[4]);
        cardList.add(m);



        cardAdapter = new CardAdapter(getContext(), cardList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

    }



    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


   /* private void getServices() {
        avi1.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.GONE);


        if (networkConnection.isNetworkConnected(getContext())) {
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
                            avi1.setVisibility(View.VISIBLE);
                            main_layout.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch Failed");
                            avi1.setVisibility(View.VISIBLE);
                            main_layout.setVisibility(View.GONE);

                        }

                        return;
                    }

                    ServiceList = response.body().getData();


                    int count = ServiceList.size();


                    if (count == 0) {
                        showMessage("Service unavailable for the moment");
                        avi1.setVisibility(View.VISIBLE);
                        main_layout.setVisibility(View.GONE);

                    } else {
                        avi1.setVisibility(View.GONE);
                        main_layout.setVisibility(View.VISIBLE);

                        cardAdapter = new CardAdapter(getContext(), ServiceList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(cardAdapter);



                    }

                }


                @Override
                public void onFailure(Call<CategoryGetObj> call, Throwable t) {
                    showMessage("Fetch failed, check your internet " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                    avi1.setVisibility(View.VISIBLE);
                    main_layout.setVisibility(View.GONE);
                }
            });

        }else{
            showMessage("No Internet connection discovered!");
            avi1.setVisibility(View.VISIBLE);
            main_layout.setVisibility(View.GONE);
        }


    }*/



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_profile:
                startActivity(new Intent(getContext(), ProfileActivity.class));
                break;

        }
    }


    private void showMessage(String s) {
        Snackbar.make(dash_layout, s, Snackbar.LENGTH_LONG).show();
    }


    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }


}
