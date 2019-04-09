package com.example.charanpuli.fish_baller.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charanpuli.fish_baller.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder  {

    public TextView itemUserName,itemUserScore,itemUserPlace;
    public CircleImageView itemUserImage;


    public UserViewHolder(@NonNull View itemView)
    {
        super(itemView);
        itemUserName=itemView.findViewById(R.id.user_name);
        itemUserScore=itemView.findViewById(R.id.user_score);
        itemUserPlace=itemView.findViewById(R.id.user_place);
        itemUserImage=itemView.findViewById(R.id.user_image);


    }
}
