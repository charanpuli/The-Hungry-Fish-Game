package com.example.charanpuli.fish_baller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.charanpuli.fish_baller.Prevalent.prevalent;


import io.paperdb.Paper;

public class ActivityGameOver extends AppCompatActivity {
    private TextView yourScore,highScore;
    private Button playAgainBtn,tellUrFrndbtn,backBtn;
    private String score;
    private String highscore="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Paper.init(this);
        String HighScore= Paper.book().read(prevalent.paperHighScore);
        if(HighScore!=""){
            if(!TextUtils.isEmpty(HighScore)) {
                highscore = Paper.book().read(prevalent.paperHighScore);
            }
        }

//        highscore="10";
        yourScore=(TextView)findViewById(R.id.yourscoreid);
        highScore=(TextView)findViewById(R.id.highscoreid);
        playAgainBtn=(Button)findViewById(R.id.playagainbtn);
        backBtn=(Button)findViewById(R.id.backbtn);

        score=getIntent().getExtras().get("score").toString();
        if(Integer.parseInt(highscore)<Integer.parseInt(score)){
            Paper.book().write(prevalent.paperHighScore,score);
            highscore=score;
        }



        yourScore.setText("Your Score : "+score);

            highScore.setText(highscore);







        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityGameOver.this,MainActivity.class);

                startActivity(intent);


            }
        });
        tellUrFrndbtn=(Button)findViewById(R.id.tellurfrndbtn);
        tellUrFrndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityGameOver.this,TellUrFriendsActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ActivityGameOver.this,StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

}
