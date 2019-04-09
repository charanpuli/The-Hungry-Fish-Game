package com.example.charanpuli.fish_baller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.charanpuli.fish_baller.ViewHolder.UserViewHolder;
import com.example.charanpuli.fish_baller.model.Share;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class LeaderActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leader);
//    }
//private EditText inputText;
//    private Button searchBtn;
    private RecyclerView searchList;
//    private String SearchInput="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);

//        inputText=findViewById(R.id.search_product_name);
//        searchBtn=findViewById(R.id.search_btn);
        searchList=findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(LeaderActivity.this));
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchInput=inputText.getText().toString();
//                onStart();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<Share> options=new FirebaseRecyclerOptions.Builder<Share>()
                .setQuery(reference.orderByChild("score"),Share.class)
                .build();


        FirebaseRecyclerAdapter<Share,UserViewHolder> adapter=new FirebaseRecyclerAdapter<Share, UserViewHolder>(options) {
            @Override
            public Share getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull final Share model)
            {
                holder.itemUserName.setText(model.getName());
                holder.itemUserPlace.setText("From : "+model.getPlace());
                holder.itemUserScore.setText("Score : "+String.valueOf(model.getScore()));

                    Picasso.get().load(model.getImage()).into(holder.itemUserImage);



            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.share_layout,parent,false);
                UserViewHolder holder=new UserViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
