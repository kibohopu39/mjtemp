package com.example.mahjongv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Begin extends AppCompatActivity {
    private Timer timer;
    private ImageView logo;
    private int nowPicPos=0;
    private int[] imgRes = {R.drawable.ic_launcher_background, R.drawable.mj11};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        logo=findViewById(R.id.logo);

        fadeOutAndHideImage(logo);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Begin.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Begin.this.finish();
            }
        },6000);
    }

    private void fadeOutAndHideImage(final ImageView img){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(2000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                nowPicPos %= 2;
                img.setImageResource(imgRes[nowPicPos]);
                nowPicPos++;
                fadeInAndShowImage(img);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }
    private void fadeInAndShowImage(final ImageView img){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(2000);

        fadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                fadeOutAndHideImage(img);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeIn);
    }

}
