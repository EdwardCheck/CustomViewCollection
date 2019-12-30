package com.mjzuo.views.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

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

    /**
     * 生成圆角图片
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float radius) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
            return bitmap;
        }catch (Exception e) {
            return bitmap;
        }
    }
}
