package com.mjzuo.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.mjzuo.views.util.CircleImageView;
import com.mjzuo.views.view.MusicBView;
import com.mjzuo.views.view.MusicRippleView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 鲸云特效
 *
 * @author mjzuo
 * @since 19/12/26
 */
public class ActivityWYCloudMusic extends BaseActivity {

    private static final int BM_PLAY_MSG = 0;

    private static final int BM_TIME = 150;

    ScheduledExecutorService sPool;
    private Runnable runnable;

    MusicBView bMusicView;
    MusicRippleView rMusicleView;

    ObjectAnimator objectAnimator0, objectAnimator1;
    CircleImageView imgRippleView, imgBView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wy_cloud_music);

        bMusicView = findViewById(R.id.view_b_music);
        rMusicleView = findViewById(R.id.view_ripple_music);

        imgRippleView = findViewById(R.id.img_ripple_icon);
        imgBView = findViewById(R.id.img_b_icon);

        runnable = new Runnable() {
            @Override
            public void run() {

                bMusicView.setColor(0xff6cbe89);
                bMusicView.start();
            }
        };

        findViewById(R.id.start_anim_wxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sPool == null) {
                    sPool = Executors.newScheduledThreadPool(1);
                    sPool.scheduleAtFixedRate(runnable, 10, 150, TimeUnit.MILLISECONDS);

                    rMusicleView.startAnima();

                    startBRotationAnim();
                    startRippleRotationAnim();
                }
            }
        });

        findViewById(R.id.stop_anim_wxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sPool != null)
                    sPool.shutdown();
                sPool = null;
                bMusicView.stop();

                rMusicleView.stopAnima();

                stopRippleRotationAnim();
                stopBRotationAnim();
            }
        });
    }

    public void startRippleRotationAnim(){

        if(objectAnimator0 == null) {
            objectAnimator0 = ObjectAnimator.ofFloat(imgRippleView, "rotation", 0f, 360f);
            objectAnimator0.setDuration(15 * 1000);
            objectAnimator0.setRepeatMode(ValueAnimator.RESTART);
            objectAnimator0.setInterpolator(new LinearInterpolator());
            objectAnimator0.setRepeatCount(-1);
        }

        objectAnimator0.start();
    }

    public void stopRippleRotationAnim() {
        if(objectAnimator0 != null)
            objectAnimator0.cancel();
    }

    public void startBRotationAnim(){

        if(objectAnimator1 == null) {
            objectAnimator1 = ObjectAnimator.ofFloat(imgBView, "rotation", 0f, 360f);
            objectAnimator1.setDuration(15 * 1000);
            objectAnimator1.setRepeatMode(ValueAnimator.RESTART);
            objectAnimator1.setInterpolator(new LinearInterpolator());
            objectAnimator1.setRepeatCount(-1);
        }

        objectAnimator1.start();
    }

    public void stopBRotationAnim() {
        if(objectAnimator1 != null)
            objectAnimator1.cancel();
    }
}
