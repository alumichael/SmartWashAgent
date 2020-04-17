package com.smartwash.smartwashagent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.smartwash.smartwashagent.Model.Category.CategoryGetData;
import com.smartwash.smartwashagent.Model.ClothList.ClothGetData;
import com.smartwash.smartwashagent.Model.OrderPost.OrderedCloths;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.Constant;
import com.smartwash.smartwashagent.activities.UserPreferences;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private Context context;
    private List<CategoryGetData> serviceList;
    String price;
    UserPreferences userPreferences;

    private MessageAdapterListener listener;



    public ServiceAdapter(Context context, List<CategoryGetData> serviceList, MessageAdapterListener listener) {
        this.context = context;
        this.serviceList = serviceList;
        this.userPreferences = new UserPreferences(context);
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryGetData serviceOption = serviceList.get(position);

        holder.mServiceName.setText(serviceOption.getCateName());
        //holder.mQuoteBuyImg.setImageResource(serviceOption.getThumbnail());
        //holder.mDsc.setText(serviceOption.get());
        price= "₦" + serviceOption.getCatePrice();
        holder.mServiceAmount.setText(price);

        holder.mDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.onDeleteClicked(Integer.parseInt(serviceOption.getCateId()));


            }
        });


        holder.setItemClickListener(pos -> {


            userPreferences.setName(serviceList.get(pos).getCateName());
            userPreferences.setPrice(serviceList.get(pos).getCatePrice());
            userPreferences.setId(serviceList.get(pos).getCateId());

            listener.onItemClicked(userPreferences.getName(),userPreferences.getPrice(),
                    userPreferences.getId());



        });
    }





    public interface MessageAdapterListener {
        void onDeleteClicked(int position);
        void onItemClicked(String cate_name,String cate_price,String cate_id);

    }




    private void nextActivity(String cate_name,String cate_price, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.CATE_NAME, cate_name);
        i.putExtra(Constant.CATE_PRICE, cate_price);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** ButterKnife Code **/

        @BindView(R.id.service_adapter_layout)
        LinearLayout mServiceAdapterLayout;
        @BindView(R.id.service_name)
        TextView mServiceName;
        @BindView(R.id.service_amount)
        TextView mServiceAmount;
        @BindView(R.id.delete_service)
        ImageView mDeleteService;

        /** ButterKnife Code **/

        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {

            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

    }
}

