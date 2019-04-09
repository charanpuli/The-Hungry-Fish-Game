package com.example.charanpuli.fish_baller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.charanpuli.fish_baller.Prevalent.prevalent;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private String checker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        setContentView(R.layout.activity_splash);

        final Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{


                        Intent intent=new Intent(SplashActivity.this,StartActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
