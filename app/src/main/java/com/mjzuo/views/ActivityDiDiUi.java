package com.mjzuo.views;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.mjzuo.views.view.PointMarkerView;
import com.mjzuo.views.view.StrokeTextView;

/**
 *  仿滴滴Ui
 *
 * @author mjzuo
 * @since 19/12/17
 */
public class ActivityDiDiUi extends BaseActivity {

    private StrokeTextView tvStrokeTxtLeft, tvStrokeTxtRight;
    private PointMarkerView pointMarkerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_didi_task);

        tvStrokeTxtLeft = findViewById(R.id.tv_stroke_left);
        tvStrokeTxtRight = findViewById(R.id.tv_stroke_right);
        pointMarkerView = findViewById(R.id.view_point_marker);

        tvStrokeTxtLeft.setTitle("从前有座山山里有个庙");
        tvStrokeTxtLeft.setDirectionType(1);
        tvStrokeTxtLeft.statrInvalidata();
        tvStrokeTxtRight.setTitle("庙里有小和尚");
        tvStrokeTxtRight.setDirectionType(0);
        tvStrokeTxtRight.statrInvalidata();

        findViewById(R.id.start_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointMarkerView.startLoadingAnima();
            }
        });

        findViewById(R.id.stop_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointMarkerView.stopLoadingAnima();
                pointMarkerView.transactionAnimWithMarker().addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        pointMarkerView.startRippleAnima();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

    }
}
