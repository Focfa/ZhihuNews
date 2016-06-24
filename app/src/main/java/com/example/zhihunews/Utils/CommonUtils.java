package com.example.zhihunews.Utils;

import android.content.Context;

/**
 * Created by Lxq on 2016/6/21.
 */

public class CommonUtils {
    /**
     *
     * @param context
     * @param px
     * @return
     */
    public static float convertPixelsToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    /**
     *
     * @param context
     * @param dp
     * @return
     */
    public static float convertDpToPixel(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
