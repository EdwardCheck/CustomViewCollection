package com.mjzuo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class CustomViewsDemo extends BaseActivity {

    /**
     * 基础绘制
     */
    public static final int NORMAL_VIEW = 0;

    /**
     * 环形进度条
     */
    public static final int RING_SCALE = 1;

    /**
     * 滴滴Ui汇总
     */
    public static final int UI_DIDI = 2;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_task);

        recyclerView = findViewById(R.id.demo_task_recycler_view);
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new CustomViewRecycler(new CustomViewRecycler.IClickListener() {
            @Override
            public void onClick(int itemPosition) {
                clickItem(itemPosition);
            }
        }));
    }

    private void clickItem(int itemPosition) {
        switch (itemPosition){
            case NORMAL_VIEW:
                toIntent(ActivityFigureView.class);
                break;
            case RING_SCALE:
                toIntent(ActivityRingScale.class);
                break;
            case UI_DIDI:
                toIntent(ActivityDiDiUi.class);
                break;
        }
    }

    private void toIntent(Class<? extends AppCompatActivity> c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
