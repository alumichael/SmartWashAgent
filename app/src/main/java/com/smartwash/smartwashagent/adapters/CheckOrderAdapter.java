package com.smartwash.smartwashagent.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderStatusData;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.AcknowledgeActivity;
import com.smartwash.smartwashagent.activities.Constant;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOrderAdapter extends RecyclerView.Adapter<CheckOrderAdapter.MyViewHolder> {

    private Context context;
    LinearLayoutManager layoutManager;
    private List<OrderStatusData> OrderStatusList;
    OrderClothsAdapter orderAdapter;


    public CheckOrderAdapter(Context context, List<OrderStatusData> OrderStatusList) {
        this.context = context;
        this.OrderStatusList = OrderStatusList;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        OrderStatusData transact = OrderStatusList.get(i);

        holder.mStatusCategory.setText(transact.getCategory());
        String amount_text="Total Amount:NGN "+transact.getTotalAmount();
        holder.mTransactAmount.setText(amount_text);
        holder.mCustomerName.setText(transact.getFullname());
        holder.mCustomerPhoneNo.setText(transact.getPhone());
        holder.mCustomerAddr.setText(transact.getAddress());

        if(transact.getStatus().equals("pending")){
            holder.mStatusFlag.setBackgroundColor(Color.RED);
            holder.mStatusFlag.setText("Pending");

        }else if(transact.getStatus().equals("delivered")){

            holder.mStatusFlag.setText("Delivered");

        }else{
            holder.mStatusFlag.setText("Received");
        }

        layoutManager = new LinearLayoutManager(context);
        holder.mRecyclerViewOrderCloths.setLayoutManager(layoutManager);

        orderAdapter = new OrderClothsAdapter(context,transact.getOrderLists());
        holder.mRecyclerViewOrderCloths.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();


        holder.mRecyclerViewOrderCloths.setVisibility(View.GONE);
        holder.mShowNothing.setVisibility(View.GONE);
        holder.mShowMore.setVisibility(View.VISIBLE);

        holder.mShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mRecyclerViewOrderCloths.setVisibility(View.VISIBLE);
                holder.mShowNothing.setVisibility(View.VISIBLE);
                holder.mShowMore.setVisibility(View.GONE);

            }
        });

        holder.mShowNothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mRecyclerViewOrderCloths.setVisibility(View.GONE);
                holder.mShowNothing.setVisibility(View.GONE);
                holder.mShowMore.setVisibility(View.VISIBLE);

            }
        });

        if(transact.getStatus().equals("pending")){

            holder.setItemClickListener(pos -> {


            /*    nextActivity(OrderStatusList.get(pos).getOrderID(),OrderStatusList.get(pos).getUserID(),"Delivered",
                        OrderStatusList.get(pos).getFullname(),OrderStatusList.get(pos).getPhone(),
                        OrderStatusList.get(pos).getAddress(),AcknowledgeActivity.class);*/

            });

        }else{
            holder.setItemClickListener(pos -> {



            });
        }



    }

    private void nextActivity(String orderId,String userId,String status,String fulllname,String phone_no,
            String address, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.ORDER_ID, orderId);
        i.putExtra(Constant.CUSTOMER_ID, userId);
        i.putExtra(Constant.ACKNWOLEDGE, status);
        i.putExtra(Constant.NAME, fulllname);
        i.putExtra(Constant.PHONE, phone_no);
        i.putExtra(Constant.ADDR1, address);
        context.startActivity(i);
    }


    @Override
    public int getItemCount() {
        return OrderStatusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** ButterKnife Code **/
        @BindView(R.id.status_list_layout)
        LinearLayout mStatusListLayout;
        @BindView(R.id.status_category)
        TextView mStatusCategory;
        @BindView(R.id.transact_amount)
        TextView mTransactAmount;
        @BindView(R.id.customer_name)
        TextView mCustomerName;
        @BindView(R.id.customer_phone_no)
        TextView mCustomerPhoneNo;
        @BindView(R.id.customer_addr)
        TextView mCustomerAddr;
        @BindView(R.id.status_flag)
        TextView mStatusFlag;
        @BindView(R.id.show_more)
        ImageButton mShowMore;
        @BindView(R.id.show_nothing)
        ImageButton mShowNothing;
        @BindView(R.id.recycler_view_order_cloths)
        RecyclerView mRecyclerViewOrderCloths;
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

