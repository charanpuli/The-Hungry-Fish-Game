package com.example.charanpuli.fish_baller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private Button startButton,leaderboardBtn;
    private TextView AdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startButton=(Button)findViewById(R.id.startBtn);
        leaderboardBtn=(Button)findViewById(R.id.leaderBtn);
        AdminLogin=(TextView)findViewById(R.id.txtloginbtn);
        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,NoticeActivity.class);
                startActivity(intent);
            }
        });
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,LeaderActivity.class);
                startActivity(intent);
            }
        });

    }
}
