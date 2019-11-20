package com.mjzuo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

public class CustomViewsDemo extends BaseActivity {

    public static final int NORMAL_VIEW = 0; //基础绘制

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_task_activity);

        recyclerView = findViewById(R.id.demo_task_recycler_view);
        initRecycler();
    }

    @Override
    String getHeadTitle() {
        return "view效果汇总";
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
                toIntent(NormalViewDrawActivity.class);
                break;
        }
    }

    private void toIntent(Class<? extends AppCompatActivity> c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
