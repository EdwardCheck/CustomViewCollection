package com.mjzuo.views.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.mjzuo.views.R;
import com.mjzuo.views.util.CommentUtils;

/**
 *
 * 这是推荐上车点的view，包括圆点和描边文字
 *
 * @author mjzuo
 * @since 19/12/17
 */
public class StrokeTextView extends View {

    // 推荐上车点的圆点
    private Paint mBitmapPaint;
    private Bitmap mCircleBitmap;
    private int mRadius;

    private Paint mTxtPaint;
    private Paint mTxtStrokePaint;
    private Rect mTxtRect;

    // 推荐上车点的文字
    private String mTitleTxt;
    private int mTxtColor;
    private int mTxtSize;

    // 描边
    private int mTxtStrokeWidth;
    private int mTxtStrokeColor;

    // 方向 0：圆点+文字 1：文字+圆点
    private int mDirectionType;
    // 圆点与图片的距离
    private int mDistance;
    // 推荐上车点一行的最大字数
    public static int MAX_TXT_SIZE = 8;
    // 推荐上车点的行间距
    private int mTxtMargin;

    private UIStyle uiStyle;

    private Context mContext;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setUiStyle();
        init();
    }

    /**
     * 设置推荐上车点的格式
     */
    public void setUiStyle() {
        this.uiStyle = new UIStyle().setTextSize(CommentUtils.sp2px(mContext.getApplicationContext(), 13))
                .setTextColor(0xFF3cbca3)
                .setBorderSize(CommentUtils.dip2px(mContext.getApplicationContext(), 2))
                .setBorderColor(0xFFFFFFFF)
                .setDotIcon(R.mipmap.pickup_cirlce_icon
                        , CommentUtils.dip2px(mContext.getApplicationContext(), 3))
                .setDistance(CommentUtils.dip2px(mContext.getApplicationContext(), 3))
                .setMaxWordsPerLine(8);
    }

    /**
     * 设置推荐上车点的内容
     * @param title
     */
    public void setTitle(String title) {
        this.mTitleTxt = title;
    }

    /**
     * 设置推荐上车点的方向
     * @param type 0：圆点+文字 1：文字+圆点
     */
    public void setDirectionType(int type) {
        this.mDirectionType = type;
    }

    /**
     * 开始刷新
     */
    public void statrInvalidata() {
        initData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mTitleTxt == null || uiStyle == null){
            return;
        }
        if(mDirectionType == 0) {
            canvas.drawBitmap(mCircleBitmap
                    , 0
                    , (mTxtRect.height() - 2 * mRadius)/2 + mTxtStrokeWidth + getPaddingTop()
                    , mBitmapPaint);
            Paint.FontMetrics fontMetrics = mTxtStrokePaint.getFontMetrics();
            mTxtPaint.setTextAlign(Paint.Align.CENTER);
            mTxtStrokePaint.setTextAlign(Paint.Align.CENTER);

            if(mTitleTxt.length() <= MAX_TXT_SIZE) {
                mTxtStrokePaint.getTextBounds(mTitleTxt, 0, mTitleTxt.length(), mTxtRect);

                canvas.drawText(mTitleTxt
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + getPaddingTop() + mTxtStrokeWidth + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);

                canvas.drawText(mTitleTxt
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

            }else{
                String line1 = mTitleTxt.substring(0, MAX_TXT_SIZE);
                String line2 = mTitleTxt.substring(MAX_TXT_SIZE);
                mTxtStrokePaint.getTextBounds(line1, 0, line1.length(), mTxtRect);

                canvas.drawText(line1
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);
                canvas.drawText(line1
                        , 2 * mRadius + mDistance + mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

                mTxtPaint.setTextAlign(Paint.Align.LEFT);
                mTxtStrokePaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(line2
                        , 2 * mRadius + mDistance + mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtStrokePaint);
                canvas.drawText(line2
                        , 2 * mRadius + mDistance + mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtPaint);
            }
        }else if(mDirectionType == 1) {

            Paint.FontMetrics fontMetrics = mTxtStrokePaint.getFontMetrics();
            mTxtPaint.setTextAlign(Paint.Align.CENTER);
            mTxtStrokePaint.setTextAlign(Paint.Align.CENTER);

            if(mTitleTxt.length() <= MAX_TXT_SIZE) {
                mTxtStrokePaint.getTextBounds(mTitleTxt, 0, mTitleTxt.length(), mTxtRect);

                canvas.drawText(mTitleTxt
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);

                canvas.drawText(mTitleTxt
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

            }else{
                String line1 = mTitleTxt.substring(0, MAX_TXT_SIZE);
                String line2 = mTitleTxt.substring(MAX_TXT_SIZE);
                mTxtStrokePaint.getTextBounds(line1, 0, line1.length(), mTxtRect);

                canvas.drawText(line1
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtStrokePaint);
                canvas.drawText(line1
                        , mTxtStrokeWidth + mTxtRect.centerX()
                        , mTxtRect.height()/2 + mTxtStrokeWidth + getPaddingTop() + (- fontMetrics.ascent)/2 - fontMetrics.descent/2
                        , mTxtPaint);

                mTxtPaint.setTextAlign(Paint.Align.LEFT);
                mTxtStrokePaint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(line2
                        , mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtStrokePaint);
                canvas.drawText(line2
                        , mTxtStrokeWidth
                        , mTxtRect.height() * 2 + mTxtMargin + 2 * mTxtStrokeWidth + getPaddingTop()
                        , mTxtPaint);
            }

            canvas.drawBitmap(mCircleBitmap
                    , mTxtRect.width() + 2 * mTxtStrokeWidth + mDistance
                    , (mTxtRect.height() - 2 * mRadius)/2 + mTxtStrokeWidth + getPaddingTop()
                    , mBitmapPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                String title = mTitleTxt;
                if(title == null){
                    return;
                }
                if(title.length() <= MAX_TXT_SIZE) {
                    mTxtStrokePaint.getTextBounds(title, 0, title.length(), mTxtRect);
                    widthSize = mTxtRect.width() + 2 * mRadius + mDistance + 2 * mTxtStrokeWidth
                            + getPaddingLeft() + getPaddingRight();
                }else{
                    mTxtStrokePaint.getTextBounds(title, 0, MAX_TXT_SIZE, mTxtRect);
                    widthSize = mTxtRect.width() + 2 * mRadius + mDistance + 2 * mTxtStrokeWidth
                            + getPaddingLeft() + getPaddingRight();
                }
                break;
            case MeasureSpec.EXACTLY:
                break;
        }

        switch (heightModel) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:

                String title = mTitleTxt;
                if(title == null){
                    return;
                }
                if(title.length() <= MAX_TXT_SIZE) {
                    mTxtStrokePaint.getTextBounds(title, 0, title.length(), mTxtRect);
                    heightSize = mTxtRect.height() + 2 * mTxtStrokeWidth
                            + getPaddingTop() +getPaddingBottom();
                }else{
                    mTxtStrokePaint.getTextBounds(title, 0, title.length(), mTxtRect);
                    heightSize = mTxtRect.height() * 2 + mTxtMargin + 4 * mTxtStrokeWidth
                            + getPaddingTop() +getPaddingBottom();
                }
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private void init() {
        mTxtPaint = new Paint();
        mTxtStrokePaint = new Paint();
        if(mTxtRect == null)
            mTxtRect = new Rect();
        mBitmapPaint = new Paint();
    }

    private void initData() {
        if(uiStyle == null)
            return;

        mRadius = uiStyle.getDotIconRadius();//3
        mTxtColor = uiStyle.getTextColor();//0xFF3cbca3
        mTxtSize = (int)(uiStyle.getTextSize());//13
        mTxtStrokeWidth = uiStyle.getBorderSize();//2
        mTxtStrokeColor = uiStyle.getBorderColor();//0xFFFFFFFF
        mDistance = uiStyle.getDistance();//3
        MAX_TXT_SIZE = uiStyle.getMaxWordsPerLine();//8

        if(mTxtPaint == null)
            mTxtPaint = new Paint();
        mTxtPaint.setColor(mTxtColor);
        mTxtPaint.setTextSize(mTxtSize);
        mTxtPaint.setTypeface(Typeface.DEFAULT_BOLD);
        if(mTxtStrokePaint == null)
            mTxtStrokePaint = new Paint();
        mTxtStrokePaint.setColor(mTxtStrokeColor);
        mTxtStrokePaint.setTextSize(mTxtSize);
        mTxtStrokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTxtStrokePaint.setStyle(Paint.Style.STROKE);
        mTxtStrokePaint.setStrokeWidth(mTxtStrokeWidth);
        mTxtStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        if(mTxtRect == null)
            mTxtRect = new Rect();

        if(mBitmapPaint == null)
            mBitmapPaint = new Paint();
        mCircleBitmap = BitmapFactory.decodeResource(mContext.getResources()
                , uiStyle.getDotIcon());//R.mipmap.pickup_cirlce_icon
        mCircleBitmap = CommentUtils.conversionBitmap(mCircleBitmap
                , mRadius * 2
                , mRadius * 2);
    }

    class UIStyle {
        private int dotIcon;
        private int dotIconRadius;

        private float textSize;
        private int textColor;
        private int borderSize;
        private int borderColor;

        private int distanceFromdotToWord;// 圆点与图片间距离

        private int maxWordsPerLine;

        /**
         * R.drawable.xxx,推荐上⻋点的原点图片
         * @param dotIcon
         * @param dotIconRadius 圆点半径
         * @return
         */
        public UIStyle setDotIcon(int dotIcon, int dotIconRadius) {
            this.dotIcon = dotIcon;
            this.dotIconRadius = dotIconRadius;
            return this;
        }

        /**
         * 上车点字体大小，单位sp，默认值12sp
         * @param textSize
         * @return
         */
        public UIStyle setTextSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 上车点颜色，16进制值
         * @param textColor
         * @return
         */
        public UIStyle setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * 推荐上⻋点文字边界宽度，单位dp，默认1dp
         * @param borderSize
         * @return
         */
        public UIStyle setBorderSize(int borderSize) {
            this.borderSize = borderSize;
            return this;
        }

        /**
         * 上车点边界颜色
         * @param borderColor
         * @return
         */
        public UIStyle setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        /**
         * 圆点与文字间的距离
         * @param distance
         */
        public UIStyle setDistance(int distance) {
            this.distanceFromdotToWord = distance;
            return this;
        }

        /**
         * 每行的最大字数，超过换行
         * @return
         */
        public UIStyle setMaxWordsPerLine(int maxWordsPerLine) {
            this.maxWordsPerLine = maxWordsPerLine;
            return this;
        }

        public int getMaxWordsPerLine() {
            return maxWordsPerLine;
        }

        public int getDistance() {
            return distanceFromdotToWord;
        }

        public int getDotIcon() {
            return dotIcon;
        }

        public int getDotIconRadius() {
            return dotIconRadius;
        }

        public float getTextSize() {
            return textSize;
        }

        public int getTextColor() {
            return textColor;
        }

        public int getBorderSize() {
            return borderSize;
        }

        public int getBorderColor() {
            return borderColor;
        }

    }
}
