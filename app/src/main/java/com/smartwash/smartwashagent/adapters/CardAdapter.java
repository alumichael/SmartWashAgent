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


import com.smartwash.smartwashagent.Model.Card;
import com.smartwash.smartwashagent.R;
import com.smartwash.smartwashagent.activities.CheckOrder;
import com.smartwash.smartwashagent.activities.Constant;
import com.smartwash.smartwashagent.activities.ManageBanner;
import com.smartwash.smartwashagent.activities.ManageClothing;
import com.smartwash.smartwashagent.activities.ManageService;
import com.smartwash.smartwashagent.activities.ManageSubAdmin;
import com.smartwash.smartwashagent.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

private Context context;
private List<Card> cardList;

public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Card cardOption = cardList.get(i);

//        bind data to view
        holder.optionTitle.setText(cardOption.getTitle());
        holder.cardLogo.setImageResource(cardOption.getThumbnail());


        holder.setItemClickListener(pos -> {
                switch (cardList.get(pos).getTitle()) {
                case "Manage Service":
                        nextActivity("Manage Service",  ManageService.class);
                        break;
                case "Manage Clothing":
                    nextActivity("Manage Clothing",  ManageClothing.class);
                        break;
                case "Check Order":
                        nextActivity("Check Order", CheckOrder.class);
                        break;
                case "Register Admin":
                    nextActivity("Register Admin",  ManageSubAdmin.class);
                        break;
                case "Manage Banner":
                        nextActivity("Manage Banner",  ManageBanner.class);
                        break;



                }

                }

                );
        }

private void nextActivity(String title, Class cardActivityClass) {
        Intent i = new Intent(context, cardActivityClass);
        i.putExtra(Constant.CARD_OPTION_TITLE, title);
        context.startActivity(i);

        }

@Override
public int getItemCount() {
        return cardList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.option_title_tv)
    TextView optionTitle;
    @BindView(R.id.option_logo)
    ImageView cardLogo;
    

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
