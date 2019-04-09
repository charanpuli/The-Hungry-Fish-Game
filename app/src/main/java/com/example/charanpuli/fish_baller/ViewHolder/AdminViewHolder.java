package com.example.charanpuli.fish_baller.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.charanpuli.fish_baller.Interface.ItemClickListener;
import com.example.charanpuli.fish_baller.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView itemUserName,itemUserScore,itemUserPlace;
    public CircleImageView itemUserImage;
    private ItemClickListener itemClickListener;


    public AdminViewHolder(@NonNull View itemView)
    {
        super(itemView);
        itemUserName=itemView.findViewById(R.id.user_name);
        itemUserScore=itemView.findViewById(R.id.user_score);
        itemUserPlace=itemView.findViewById(R.id.user_place);
        itemUserImage=itemView.findViewById(R.id.user_image);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

