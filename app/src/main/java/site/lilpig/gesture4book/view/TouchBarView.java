package site.lilpig.gesture4book.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.RequiresApi;
import java.util.Date;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.handler.GestureDirection;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.handler.GestureMetaData;
import site.lilpig.gesture4book.handler.GestureType;
import site.lilpig.gesture4book.util.LoggerFactory;
import site.lilpig.gesture4book.util.DisplayUtil;

public class TouchBarView extends View{
    private LoggerFactory logger = new LoggerFactory("TouchBarView");

    private GestureHandler topHandler = null;
    private GestureHandler bottomHandler = null;
    private GestureHandler leftHandler = null;
    private GestureHandler rightHandler = null;

    private int triggerDistance = DisplayUtil.px2dip(getContext(),20);
    private int triggerHoverTime = 500;

    private Paint paint;
    public TouchBarView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0x00000000);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setBottomHandler(GestureHandler bottomHandler) {
        this.bottomHandler = bottomHandler;
    }

    public void setLeftHandler(GestureHandler leftHandler) {
        this.leftHandler = leftHandler;
    }

    public void setRightHandler(GestureHandler rightHandler) {
        this.rightHandler = rightHandler;
    }

    public void setTopHandler(GestureHandler topHandler) {
        this.topHandler = topHandler;
    }


    private float startX;
    private float startY;
    private GestureHandler curHandler;
    private long curHandlerStartTime = 0;
    private float currentDistance;
    private int currentDirection;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = event.getAction();
        if (actionId == MotionEvent.ACTION_DOWN){
            startX = event.getRawX();
            startY = event.getRawY();
        }else if (actionId == MotionEvent.ACTION_MOVE){
            float curX = event.getRawX();
            float curY = event.getRawY();
            GestureHandler tmpHandler = null;
            if ((currentDistance=curX-startX) >= triggerDistance) {
                tmpHandler = rightHandler;
                currentDirection = GestureDirection.DIRE_RIGHT;
            }else if ((currentDistance=startX-curX) >= triggerDistance){
                tmpHandler = leftHandler;
                currentDirection = GestureDirection.DIRE_LEFT;
            }else if ((currentDistance=curY-startY) >= triggerDistance){
                tmpHandler = bottomHandler;
                currentDirection = GestureDirection.DIRE_BOTTOM;
            }else if ((currentDistance=startY-curY) >= triggerDistance){
                tmpHandler = topHandler;
                currentDirection = GestureDirection.DIRE_TOP;
            }else {
                tmpHandler = null;
            }

            if (tmpHandler != curHandler){
                curHandlerStartTime = new Date().getTime();
                curHandler = tmpHandler;
            }else if(curHandler != null){
                long timePast = new Date().getTime() - curHandlerStartTime;
                if (timePast > triggerHoverTime){
                    curHandler.onActive(new GestureMetaData(getContext(),GestureType.TYPE_HOVER,currentDistance,currentDirection));
                }
            }
        }else if (actionId == MotionEvent.ACTION_UP){
            if (curHandler!=null){
               if (curHandler.isActive() == -1){
                   curHandler.onActive(new GestureMetaData(getContext(),GestureType.TYPE_NORMAL,currentDistance,currentDirection));
               }else {
                   curHandler.onExit();
               }
            }
            curHandlerStartTime = 0;
            curHandler = null;
            startX = 0;
            startY = 0;
            currentDirection = 0;
            currentDistance = 0;
        }
        return super.onTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),10,10,paint);
    }

}
