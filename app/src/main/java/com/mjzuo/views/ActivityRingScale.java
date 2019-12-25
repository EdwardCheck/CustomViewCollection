package com.mjzuo.views;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mjzuo.views.view.RingScaleView;
import com.mjzuo.views.view.SeekController;
import com.mjzuo.views.view.SlideRingScaleView;

public class ActivityRingScale extends BaseActivity {

    private SeekController seekController;
    private RingScaleView ringScaleView;
    private SlideRingScaleView slideRingScaleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_scale_task);

        seekController = findViewById(R.id.seek_controllor);
        ringScaleView = findViewById(R.id.ring_scale_view);
        slideRingScaleView = findViewById(R.id.slide_ring_scale_view);

        seekController.setOnScaleChangeListener(new SeekController.OnScaleChangeListener() {
            @Override
            public void onScaleBy(int scaleBy) {
                // 刻度变化监听
                ringScaleView.setProgress(scaleBy);
                slideRingScaleView.setProgress(scaleBy);
            }
        });
    }
}
