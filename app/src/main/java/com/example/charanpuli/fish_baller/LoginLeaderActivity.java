package com.example.charanpuli.fish_baller;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.charanpuli.fish_baller.ViewHolder.AdminViewHolder;
import com.example.charanpuli.fish_baller.ViewHolder.UserViewHolder;
import com.example.charanpuli.fish_baller.model.Share;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class LoginLeaderActivity extends AppCompatActivity {
    private RecyclerView searchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_leader);
        searchList=findViewById(R.id.admin_search_list);
        searchList.setLayoutManager(new LinearLayoutManager(LoginLeaderActivity.this));

    }
    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<Share> options=new FirebaseRecyclerOptions.Builder<Share>()
                .setQuery(reference.orderByChild("score"),Share.class)
                .build();


        FirebaseRecyclerAdapter<Share,AdminViewHolder> adapter=new FirebaseRecyclerAdapter<Share, AdminViewHolder>(options) {
            @Override
            public Share getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }

            @Override
            protected void onBindViewHolder(@NonNull final AdminViewHolder holder, int position, @NonNull final Share model)
            {
                holder.itemUserName.setText(model.getName());
                holder.itemUserPlace.setText("From : "+model.getPlace());
                holder.itemUserScore.setText("Score : "+String.valueOf(model.getScore()));

                Picasso.get().load(model.getImage()).into(holder.itemUserImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                        CharSequence[] options=new CharSequence[]{
                                "Remove"
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(LoginLeaderActivity.this);

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    if(which==0){
                                        reference1.child("Users")
                                                .child(model.getPid())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(LoginLeaderActivity.this, "Removed Successfully...", Toast.LENGTH_SHORT).show();
                                                        onStart();
                                                    }
                                                });
                                    }
                            }
                        });
                        builder.show();



                    }
                });



            }

            @NonNull
            @Override
            public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.share_layout,parent,false);
                AdminViewHolder holder=new AdminViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
