package com.mjzuo.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mjzuo.views.view.GeometricFigureView;

/**
 *  view的基础绘制类
 *
 * @author mjzuo
 * @since 19/11/19
 */
public class NormalViewDrawActivity extends BaseActivity implements View.OnClickListener {

    // 基础图形绘制类view
    GeometricFigureView figureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_view_draw_activity);

        init();
    }

    @Override
    String getHeadTitle() {
        return "基础图形绘制";
    }

    private void init() {
        figureView = findViewById(R.id.view_figure);

        findViewById(R.id.view_line).setOnClickListener(this);
        findViewById(R.id.view_rect).setOnClickListener(this);
        findViewById(R.id.view_circle).setOnClickListener(this);
        findViewById(R.id.view_oval).setOnClickListener(this);
        findViewById(R.id.view_round_rect).setOnClickListener(this);
        findViewById(R.id.view_arc).setOnClickListener(this);
        findViewById(R.id.view_more_figure).setOnClickListener(this);
        findViewById(R.id.view_color).setOnClickListener(this);
        findViewById(R.id.view_text).setOnClickListener(this);
        findViewById(R.id.view_bitmap).setOnClickListener(this);
        findViewById(R.id.view_crop).setOnClickListener(this);
        findViewById(R.id.view_rotate).setOnClickListener(this);
        findViewById(R.id.view_translate).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_line:
                changeViewType(GeometricFigureView.LINE_TYPE);
                break;
            case R.id.view_rect:
                changeViewType(GeometricFigureView.RECT_TYPE);
                break;
            case R.id.view_circle:
                changeViewType(GeometricFigureView.CIRCLE_TYPE);
                break;
            case R.id.view_oval:
                changeViewType(GeometricFigureView.OVAL_TYPE);
                break;
            case R.id.view_round_rect:
                changeViewType(GeometricFigureView.ROUND_CIRCLE_TYPE);
                break;
            case R.id.view_arc:
                changeViewType(GeometricFigureView.ARC_TYPE);
                break;
            case R.id.view_more_figure:
                changeViewType(GeometricFigureView.MORE_FIGURE_TYPE);
                break;
            case R.id.view_color:
                changeViewType(GeometricFigureView.FILL_CORLOR);
                break;
            case R.id.view_text:
                changeViewType(GeometricFigureView.TEXT_CORLOR);
                break;
            case R.id.view_bitmap:
                changeViewType(GeometricFigureView.BITMAP_TYPE);
                break;
            case R.id.view_crop:
                changeViewType(GeometricFigureView.CLIP_TYPE);
                break;
            case R.id.view_rotate:
                changeViewType(GeometricFigureView.STATE_TYPE);
                break;
            case R.id.view_translate:
                changeViewType(GeometricFigureView.TRANSLATE_TYPE);
                break;
        }
    }

    private void changeViewType(int type) {
        figureView.setDrawType(type);
    }
}
