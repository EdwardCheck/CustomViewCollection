package com.mjzuo.views.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtils {

    /**
     *  转换bitmap宽高
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
