package com.example.hiros.sharetaxi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splashscreen extends Activity{
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }
    private  void StartAnimations(){
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        LinearLayout I = (LinearLayout) findViewById(R.id.lin_lay);
        I.clearAnimation();
        I.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this,R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread(){
            @Override
            public  void run(){
                try {
                    int waited = 0;
                    while(waited < 20000){
                        sleep(20);
                        waited += 100;
                    }

                    Intent intent = new Intent(Splashscreen.this, /*MainActivity.class*/LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                }catch (InterruptedException e){
                    //do nothing
                }finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();
    }
}