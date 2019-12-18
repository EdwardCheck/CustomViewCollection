package com.mjzuo.views.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class CommentUtils {

    /**
     *  根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }

    /**
     * 缩放到固定尺寸
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap conversionBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap b = bitmap;
        int width = b.getWidth();
        int height = b.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
    }
}
