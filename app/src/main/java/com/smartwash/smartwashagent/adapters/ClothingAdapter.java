package com.smartwash.smartwashagent.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwash.smartwashagent.Model.ClothList.ClothGetData;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.Constant;
import com.smartwash.smartwashagent.activities.UserPreferences;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.MyViewHolder> {

    private Context context;
    private List<ClothGetData> clothList;
    String price;
    UserPreferences userPreferences;




    private MessageAdapterListener listener;



    public ClothingAdapter(Context context, List<ClothGetData> clothList, MessageAdapterListener listener) {
        this.context = context;
        this.clothList = clothList;
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
        ClothGetData clothOption = clothList.get(position);

        holder.mServiceName.setText(clothOption.getClothName());


        if(!clothOption.getClothAmount().equals("0")){

            holder.mServiceAmount.setVisibility(View.VISIBLE);

            if(clothOption.getClothName().equals("Liquid Soap")){
                String clothe_type="₦ "+clothOption.getClothAmount()+": / Litre";
                holder.mServiceAmount.setText(clothe_type);
            }else{
                String clothe_type="Special Cloth: ₦ "+clothOption.getClothAmount();
                holder.mServiceAmount.setText(clothe_type);
            }

        }else{
            holder.mServiceAmount.setVisibility(View.GONE);
        }



        holder.mDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.onDeleteClicked(Integer.parseInt(clothOption.getClothId()));


            }
        });


        holder.setItemClickListener(pos -> {


            userPreferences.setName(clothList.get(pos).getClothName());
            userPreferences.setPrice(clothList.get(pos).getClothAmount());
            userPreferences.setId(clothList.get(pos).getClothId());

            listener.onItemClicked(userPreferences.getName(),userPreferences.getPrice(),
                    userPreferences.getId());



        });
    }





    public interface MessageAdapterListener {
        void onDeleteClicked(int position);
        void onItemClicked(String cate_name, String cate_price, String cate_id);

    }




    private void nextActivity(String cate_name,String cate_price, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.CATE_NAME, cate_name);
        i.putExtra(Constant.CATE_PRICE, cate_price);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return clothList.size();
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

