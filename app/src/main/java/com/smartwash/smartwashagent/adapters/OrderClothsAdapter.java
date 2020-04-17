package com.smartwash.smartwashagent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartwash.smartwashagent.Model.OrderStatusGet.OrderList;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderClothsAdapter extends RecyclerView.Adapter<OrderClothsAdapter.MyViewHolder> {

    private Context context;

    private List<OrderList> orderLists;


    public OrderClothsAdapter(Context context, List<OrderList> orderLists) {
        this.context = context;
        this.orderLists = orderLists;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        OrderList ordered = orderLists.get(i);
        holder.cloth_name.setText(ordered.getCloth());
        holder.count_text.setText(String.valueOf(ordered.getQuantity()));

    }


    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.cloth_name)
        TextView cloth_name;
        @BindView(R.id.count_text)
        TextView count_text;


        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

           // itemView.setOnClickListener(this);
        }


    }
}

