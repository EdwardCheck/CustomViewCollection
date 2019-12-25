package com.mjzuo.views.helper;

import android.content.Context;
import android.content.res.Resources;

public class ResourceHelper {

    private static int getResId(Context context, String resName) {
        Resources r = context.getResources();
        int resId = r.getIdentifier(resName, "drawable", context.getPackageName());
        return resId;
    }

    private static int getColorResId(Context context, String resName) {
        Resources r = context.getResources();
        int resId = r.getIdentifier(resName, "color", context.getPackageName());
        return resId;
    }
}
