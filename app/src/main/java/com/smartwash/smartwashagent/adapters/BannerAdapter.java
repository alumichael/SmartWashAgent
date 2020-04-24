package com.smartwash.smartwashagent.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smartwash.smartwashagent.Model.Banner.BannerGetData;

import com.smartwash.smartwashagent.Model.Category.CategoryGetData;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.CheckOrder;
import com.smartwash.smartwashagent.activities.Constant;
import com.smartwash.smartwashagent.activities.ManageBanner;
import com.smartwash.smartwashagent.activities.ManageClothing;
import com.smartwash.smartwashagent.activities.ManageService;
import com.smartwash.smartwashagent.activities.ManageSubAdmin;
import com.smartwash.smartwashagent.activities.UserPreferences;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {

    private Context context;
    private List<BannerGetData> bannerList;
    private BannerAdapter.MessageAdapterListener listener;
    UserPreferences userPreferences;


    public BannerAdapter(Context context, List<BannerGetData> bannerList, BannerAdapter.MessageAdapterListener listener) {
        this.context = context;
        this.bannerList = bannerList;
        this.userPreferences = new UserPreferences(context);
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        BannerGetData bannerOption = bannerList.get(i);

//        bind data to view

        if(bannerOption.getBannerUrl()==null) {
           holder.mBannerImg.setImageResource(R.drawable.img_empty);

        }else if(!bannerOption.getBannerUrl().startsWith("h")){
            holder.mBannerImg.setImageResource(R.drawable.img_empty);
        }else{

            Glide.with(context).load(bannerOption.getBannerUrl()).apply(new RequestOptions().fitCenter().circleCrop()).into(holder.mBannerImg);

        }
        
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onDeleteClicked(Integer.parseInt(bannerOption.getBannerId()));

            }
        });


        

    }

    public interface MessageAdapterListener {
        void onDeleteClicked(int position);


    }


    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.banner_img)
        ImageView mBannerImg;
        @BindView(R.id.delete_btn)
        ImageView deleteBtn;


        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

          //  itemView.setOnClickListener(this);
        }




    }
}
