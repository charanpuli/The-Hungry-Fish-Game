package com.example.charanpuli.fish_baller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.charanpuli.fish_baller.Prevalent.prevalent;
import com.ncorti.slidetoact.SlideToActView;

import org.jetbrains.annotations.NotNull;

import io.paperdb.Paper;

public class NoticeActivity extends AppCompatActivity {
    SlideToActView slideToActView;
    private String Checked="checked";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        slideToActView=(SlideToActView)findViewById(R.id.slidetounlock);

        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull SlideToActView slideToActView) {

                Intent intent=new Intent(NoticeActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
    }
}
