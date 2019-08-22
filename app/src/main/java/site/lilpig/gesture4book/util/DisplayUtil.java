package site.lilpig.gesture4book.util;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayUtil {
    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //px转dp
    public static int px2dip(Context context, int pxValue) {
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics()));
    }

    public static int[] getDeviceWH(Context context){
        DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return new int[]{width,height};
    }
}
