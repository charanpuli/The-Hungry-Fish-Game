package com.example.charanpuli.fish_baller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap[] fish=new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint scorePaint=new Paint();
    private Bitmap[] life=new Bitmap[2];
    private Paint higher=new Paint();
    private Paint comment=new Paint();
    private int lifeCount=3;

    private int fishX=10;
    private int fishY;
    private int fishSpeed;
    private int highscore=0;
    private int score;


    private int canvasWidth,canvasHeight;
    private Bitmap algae,insect,red,bomb;
    private int algaeX,algaeY,algaeSpeed=14;
    private int insectX,insectY,insectSpeed=16;
    private int redX,redY,redSpeed=18;
    private int bombX,bombY,bombSpeed=12;
    private long mStartTime;

    private boolean touch=false;
    public FlyingFishView(Context context) {
        super(context);
        fish[0]=BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]=BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.water);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        higher.setColor(Color.BLACK);
        higher.setTextSize(70);
        higher.setAntiAlias(true);
        higher.setTypeface(Typeface.DEFAULT_BOLD);
        comment.setTypeface(Typeface.DEFAULT_BOLD);
        comment.setTextSize(70);
        comment.setAntiAlias(true);
        comment.setColor(Color.RED);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        algae=BitmapFactory.decodeResource(getResources(),R.drawable.algae);
        insect=BitmapFactory.decodeResource(getResources(),R.drawable.insect);
        red=BitmapFactory.decodeResource(getResources(),R.drawable.octopus);
        bomb=BitmapFactory.decodeResource(getResources(),R.drawable.bomb1);

        fishY=550;
        score=0;
        mStartTime = System.currentTimeMillis();



    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();
        canvas.drawBitmap(backgroundImage,0,150,null);
        int minfishY=fish[0].getHeight();
        int maxfishY=canvasHeight-fish[0].getHeight()*3;

        fishY=fishY+fishSpeed;

        if(fishY<minfishY){
            fishY=minfishY;

        }
        if(fishY>maxfishY){
            fishY=maxfishY;
        }
        fishSpeed=fishSpeed+2;
        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }
        else{
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }
        ///////algae//////
        algaeX=algaeX-algaeSpeed;
        if(hitBallChecker(algaeX,algaeY)){
            score=score+10;
            algaeX=-10;
        }

        if(algaeX<0){
            algaeX=canvasWidth+21;
            algaeY= (int) (Math.floor(Math.random()*(maxfishY-minfishY))+minfishY);
        }

        canvas.drawBitmap(algae,algaeX,algaeY,null);
        //////insect///////
        insectX=insectX-insectSpeed;
        if(hitBallChecker(insectX,insectY)){
            score=score+15;
            insectX=-10;
        }

        if(insectX<0){
            insectX=canvasWidth+21;
            insectY= (int) (Math.floor(Math.random()*(maxfishY-minfishY))+minfishY);
        }

        canvas.drawBitmap(insect,insectX,insectY,null);
        ////octopus/////
        redX=redX-redSpeed;
        if(hitBallChecker(redX,redY)){

            redX=-10;
            lifeCount--;
            if(lifeCount==0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),ActivityGameOver.class);

                intent.putExtra("score",score);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);



            }
        }

        if(redX<0){
            redX=canvasWidth+40;
            redY= (int) (Math.floor(Math.random()*(maxfishY-minfishY))+minfishY);
        }

        canvas.drawBitmap(red,redX,redY,null);


        canvas.drawText("Score : "+score,20,80,scorePaint);
        for(int i=0;i<3;i++){
            int x=(int)((canvasWidth-400)+life[0].getWidth()*1.5*i);
            int y=20;
            if(i<lifeCount){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

        if((System.currentTimeMillis()-mStartTime)/1000<6) {
            canvas.drawText("Note : Beware of Red Octopus...", 60, canvasHeight - 80, comment);
        }
        if((System.currentTimeMillis()-mStartTime)/1000>6 && (System.currentTimeMillis()-mStartTime)/1000<10) {
            canvas.drawText("All The Best...", 80, canvasHeight - 80, comment);
        }
        /////bomb//////
        bombX=bombX-bombSpeed;
        if(hitBallChecker(bombX,bombY)){

            bombX=-10;

                Toast.makeText(getContext(), "Fish Died", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),ActivityGameOver.class);

                intent.putExtra("score",score);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);




        }

        if(bombX<0){
            bombX=canvasWidth+100;
            bombY= (int) (Math.floor(Math.random()*(maxfishY-minfishY))+minfishY);
        }









        canvas.drawBitmap(bomb,bombX,bombY,null);
//        canvas.drawBitmap(life[0],canvasWidth-200,10,null);
//        canvas.drawBitmap(life[0],canvasWidth-100,10,null);

    }
    public boolean hitBallChecker(int x,int y){
        if(fishX<x && x<fishX+fish[0].getWidth() && fishY<y && y<fishY+fish[0].getHeight()){
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed=-22;

        }
        return true;
    }
}
