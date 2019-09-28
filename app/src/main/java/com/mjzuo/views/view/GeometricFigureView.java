package com.mjzuo.views.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mjzuo.views.R;
import com.mjzuo.views.util.BitmapUtils;
import com.mjzuo.views.util.CommentUtils;

/**
 *  画几何形状的View
 *
 * @author mingjiezuo
 * @since 19/09/08
 */
public class GeometricFigureView extends View {

    private static final int LINE_TYPE = 0;
    private static final int RECT_TYPE = 1;
    private static final int CIRCLE_TYPE = 2;
    private static final int OVAL_TYPE = 3;
    private static final int ROUND_CIRCLE_TYPE = 4;
    private static final int ARC_TYPE = 5;
    private static final int FILL_CORLOR = 7;
    private static final int TEXT_CORLOR = 8;
    private static final int BITMAP_TYPE = 9;

    /**
     *  当前画形状的type：
     *   0：线
     *   1：矩形
     *   2：圆形
     *   3：椭圆
     *   4：圆角矩形
     *   5：扇形
     *   6：多边形
     *   7：填充颜色
     *   8：文本
     *   9: bitmap
     */
    private int mDrawType;

    private int defStyleAttr;

    /**
     *  画线段的画笔
     */
    private Paint linePaint;

    /**
     *  矩形的rectF
     */
    private RectF rectF;

    /**
     *  矩形的画笔
     */
    private Paint rectPaint;

    /**
     *  圆形的画笔
     */
    private Paint circlePaint;

    /**
     *  圆角矩形的rectF
     */
    private RectF mRoundRectF;

    /**
     *  圆角矩形的画笔
     */
    private Paint mRoundRectFPaint;

    /**
     * 椭圆的外切rectF
     */
    private RectF mOvalRectF;

    /**
     * 椭圆的画笔
     */
    private Paint mOvalPaint;

    /**
     * 弧的rectF
     */
    private RectF mArcRectF;

    /**
     * 弧的画笔
     */
    private Paint mArcPaint;

    /**
     * 文本的画笔
     */
    private Paint textPaint;

    /**
     * 绘制图片的bitmap
     */
    private Bitmap mBitmap;

    /**
     * bitmap画笔
     */
    private Paint mBitmapPaint;

    private Context mContext;

    public GeometricFigureView(Context context) {
        this(context, null);
    }

    public GeometricFigureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GeometricFigureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.defStyleAttr = defStyleAttr;
        initView(attrs);
        initPaint();
    }

    public void setDrawType(int drawType) {
        mDrawType = drawType;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mDrawType){
            case LINE_TYPE:
                drawLine(canvas);
                break;
            case RECT_TYPE:
                drawRect(canvas);
                break;
            case CIRCLE_TYPE:
                drawCircle(canvas);
                break;
            case OVAL_TYPE:
                drawOval(canvas);
                break;
            case ROUND_CIRCLE_TYPE:
                drawRoundRect(canvas);
            case ARC_TYPE:
                drawArc(canvas);
                break;
            case FILL_CORLOR:
                drawCorlor(canvas);
                break;
            case TEXT_CORLOR:
                drawText(canvas);
                break;
            case BITMAP_TYPE:
                drawBitmap(canvas);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = mContext.getTheme().obtainStyledAttributes(attrs
                    , R.styleable.GeometricFigureView
                    , defStyleAttr, 0);
            mDrawType = array.getInt(R.styleable.GeometricFigureView_draw_type, 0);
            //记得使用完销毁
            array.recycle();
        }
    }

    private void initPaint() {
        // 画线
        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);

        // 画矩形
        rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);

        // 画圆形
        circlePaint = new Paint();
        circlePaint.setColor(0xFFCCFFFF);
//        circlePaint.setStyle(Paint.Style.FILL);// 充满
        circlePaint.setStyle(Paint.Style.STROKE);// 镶边

        // 画圆角矩形
        mRoundRectFPaint = new Paint();
        mRoundRectFPaint.setColor(0xFFCCFFFF);

        // 圆角矩形的画笔
        mOvalPaint = new Paint();
        mOvalPaint.setColor(0xFFCCFFFF);

        // 弧
        mArcPaint = new Paint();
        mArcPaint.setColor(0xFFCCFFFF);

        // 文本
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(CommentUtils.sp2px(mContext, 13));

        // 位图
        mBitmapPaint = new Paint();
    }

    /**
     *  画线的方法
     */
    private void drawLine(Canvas canvas) {
        /**
         * @params startX 线段起点的x坐标
         * @params startY 线段起点的Y坐标
         * @params stopX 线段终点的x坐标
         * @params stopY 线段终点y的坐标
         */
        canvas.drawLine(0,0
                , CommentUtils.dip2px(mContext, 150)
                , CommentUtils.dip2px(mContext, 150)
                , linePaint);
    }

    /**
     *  画矩形的方法
     */
    private void drawRect(Canvas canvas) {
        /**
         * RectF:
         *  left 矩形左侧的x坐标
         *  top 矩形顶部的y坐标
         *  right 矩形右侧的x坐标
         *  bottom 矩形底部的y坐标
         */
        if(rectF == null)
            rectF = new RectF(0, 0
                    , CommentUtils.dip2px(mContext, 150)// 单位都是px
                    , CommentUtils.dip2px(mContext, 75));
        canvas.drawRect(rectF, rectPaint);
    }

    /**
     *  画圆的方法
     */
    private void drawCircle(Canvas canvas) {
        /**
         *  float cx 中心点的x坐标
         *  float cy 中心点的y坐标
         *  float radius 半径
         */
        canvas.drawCircle(CommentUtils.dip2px(mContext, 37.5f)
                , CommentUtils.dip2px(mContext, 37.5f)
                , CommentUtils.dip2px(mContext, 37.5f)
                , circlePaint);
    }

    /**
     *  圆角矩形
     */
    private void drawRoundRect(Canvas canvas) {
        /**
         * RectF：矩形区域
         * rx：在x轴的半径，焦点在x轴的椭圆长半轴
         * ry：在y轴的半径，焦点在x轴的椭圆短半轴
         *  可以理解成，在rectF矩形左上角的一个长轴短轴分别为2rx、2ry的标准内切椭圆
         */
        if(mRoundRectF == null)
            mRoundRectF = new RectF(0, 0
                    , CommentUtils.dip2px(mContext, 150)// 单位都是px
                    , CommentUtils.dip2px(mContext, 75));
        canvas.drawRoundRect(mRoundRectF
                , CommentUtils.dip2px(mContext, 36.5f)
                , CommentUtils.dip2px(mContext, 18.25f)
                , mRoundRectFPaint);
    }

    /**
     * 画椭圆
     */
    private void drawOval(Canvas canvas) {
        if(mOvalRectF == null)
            mOvalRectF = new RectF(0, 0
                    , CommentUtils.dip2px(mContext, 75)// 单位都是px
                    , CommentUtils.dip2px(mContext, 37.5f));
        canvas.drawOval(mOvalRectF, mOvalPaint);
    }

    /**
     * 画弧
     */
    private void drawArc(Canvas canvas) {
        /**
         * RectF：矩形边界
         * startAngle：开始弧的角度，手表3点钟的方向为0
         * sweepAngle：顺时针的扫描角度
         * useCenter：椭圆的中心是否包含在弧里
         */
        if(mArcRectF == null)
            mArcRectF = new RectF(0, 0
                    , CommentUtils.dip2px(mContext, 150)// 单位都是px
                    , CommentUtils.dip2px(mContext, 75));
        canvas.drawArc(mArcRectF
                , -90
                , 180
                , true
                , mArcPaint);
    }

    /**
     * 填充颜色
     */
    private void drawCorlor(Canvas canvas) {
        canvas.drawColor(0xFFCCFFFF, PorterDuff.Mode.SRC_OVER);
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        /**
         *  text：绘制文本
         *  textX：绘制文本的原点x坐标
         *  textY：绘制文本基线的y坐标
         */
        canvas.drawText("我和我的祖国"
                , CommentUtils.dip2px(mContext, 75)
                , CommentUtils.dip2px(mContext, 75)
                , textPaint);
    }

    /**
     * 绘制图片
     */
    private void drawBitmap(Canvas canvas) {
        /**
         * bitmap
         *  left：绘制的位图的左侧位置
         *  top：绘制位图的上方位置
         */
        if(mBitmap == null){
            // 将资源图片转换成bitmap,R.mipmap.android:资源图片
            mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_android);
            // 将mBitmap缩放成固定大小
            mBitmap = BitmapUtils.conversionBitmap(mBitmap
                    , CommentUtils.dip2px(mContext, 42)
                    , CommentUtils.dip2px(mContext, 42));
        }
        canvas.drawBitmap(mBitmap
                , 0
                , 0
                , mBitmapPaint);
    }
}
