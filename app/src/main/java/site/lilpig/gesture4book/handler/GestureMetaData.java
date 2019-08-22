package site.lilpig.gesture4book.handler;

import android.content.Context;

public class GestureMetaData {
    public final Context context;
    public final int gestureType;
    public final float distance;
    public final int direction;

    public GestureMetaData(Context context, int gestureType, float distance, int direction) {
        this.context = context;
        this.gestureType = gestureType;
        this.distance = distance;
        this.direction = direction;
    }
}
