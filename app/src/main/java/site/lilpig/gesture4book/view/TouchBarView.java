package site.lilpig.gesture4book.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.RequiresApi;
import java.util.Date;

import site.lilpig.gesture4book.support.GestureDirection;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.support.GestureMetaData;
import site.lilpig.gesture4book.support.GestureType;
import site.lilpig.gesture4book.util.LoggerFactory;
import site.lilpig.gesture4book.util.DisplayUtil;

public class TouchBarView extends View{
    private LoggerFactory logger = new LoggerFactory("TouchBarView");

    private GestureHandler[] normalHandlers = new GestureHandler[4];
    private GestureHandler[] hoverHandlers = new GestureHandler[4];

    private int triggerDistance = DisplayUtil.px2dip(getContext(),40);
    private int triggerHoverTime = 500;

    private Paint paint;
    public TouchBarView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.argb(50,0,0,0));
    }

    public void setBottomHandler(GestureHandler bottomHandler) {
        this.normalHandlers[GestureDirection.DIRE_BOTTOM] = bottomHandler;
    }

    public void setLeftHandler(GestureHandler leftHandler) {
        this.normalHandlers[GestureDirection.DIRE_LEFT] = leftHandler;
    }

    public void setRightHandler(GestureHandler rightHandler) {
        this.normalHandlers[GestureDirection.DIRE_RIGHT] = rightHandler;
    }

    public void setTopHandler(GestureHandler topHandler) {
        this.normalHandlers[GestureDirection.DIRE_TOP] = topHandler;
    }

    public void setBottomHoverHandler(GestureHandler bottomHoverHandler) {
        this.hoverHandlers[GestureDirection.DIRE_BOTTOM] = bottomHoverHandler;
    }

    public void setLeftHoverHandler(GestureHandler leftHoverHandler) {
        this.hoverHandlers[GestureDirection.DIRE_LEFT] = leftHoverHandler;
    }

    public void setRightHoverHandler(GestureHandler rightHoverHandler) {
        this.hoverHandlers[GestureDirection.DIRE_RIGHT] = rightHoverHandler;
    }

    public void setTopHoverHandler(GestureHandler topHoverHandler) {
        this.hoverHandlers[GestureDirection.DIRE_TOP] = topHoverHandler;
    }

    public void setAlpha(int alpha){
        paint.setColor(Color.argb(alpha,0,0,0));
        postInvalidate();
    }


    private float startX;
    private float startY;
    private long curHandlerStartTime = 0;
    private float currentDistance;
    private int currentDirection = GestureDirection.DIRE_NONE;
    private boolean isHoverHandlerInvoked = false;
    /**
     * onTouchEvent analysis user gesture and invoke the life-cycle method of the corresponding GestureHandler
     *
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionId = event.getAction();
        if (actionId == MotionEvent.ACTION_DOWN){
            startX = event.getRawX();
            startY = event.getRawY();
        }else if (actionId == MotionEvent.ACTION_MOVE){
            float curX = event.getRawX();
            float curY = event.getRawY();
            int tmpDir = GestureDirection.DIRE_NONE;
            if ((currentDistance=curX-startX) >= triggerDistance) {
                tmpDir = GestureDirection.DIRE_RIGHT;
            }else if ((currentDistance=startX-curX) >= triggerDistance){
                tmpDir = GestureDirection.DIRE_LEFT;
            }else if ((currentDistance=curY-startY) >= triggerDistance){
                tmpDir = GestureDirection.DIRE_BOTTOM;
            }else if ((currentDistance=startY-curY) >= triggerDistance){
                tmpDir = GestureDirection.DIRE_TOP;
            }else {
                tmpDir = GestureDirection.DIRE_NONE;
            }


            //The following code looks a little ugly, but I don't want to rewrite it.
            if (tmpDir != currentDirection){
                //Reset hover state and invoke the exit callback of last handler.
                if (isHoverHandlerInvoked){
                    if (currentDirection!=GestureDirection.DIRE_NONE && hoverHandlers[currentDirection]!=null && hoverHandlers[currentDirection].isActive() == GestureType.TYPE_HOVER)
                        hoverHandlers[currentDirection].onExit();
                    isHoverHandlerInvoked = false;
                }
                //Reset start time and direction.
                curHandlerStartTime = new Date().getTime();
                currentDirection = tmpDir;
            }

            //Invoke hover callback
            long timePast = new Date().getTime() - curHandlerStartTime;
            if (currentDirection == GestureDirection.DIRE_NONE)return false;
            if (timePast >= triggerHoverTime && hoverHandlers[currentDirection] != null
                    && hoverHandlers[currentDirection].isActive() == GestureType.TYPE_NONE){
                hoverHandlers[currentDirection].onActive(new GestureMetaData(getContext(),GestureType.TYPE_HOVER,currentDistance,currentDirection));
                isHoverHandlerInvoked = true;
            }
        }else if (actionId == MotionEvent.ACTION_UP){
            if (currentDirection == GestureDirection.DIRE_NONE)
                return false;
            if (!isHoverHandlerInvoked){
                //Normal gesture
                GestureHandler handler = normalHandlers[currentDirection];
                if (handler!=null && handler.isActive() == GestureType.TYPE_NONE)
                    handler.onActive(new GestureMetaData(getContext(),GestureType.TYPE_NORMAL,currentDistance,currentDirection));
            }else{
                //It must be hover gesture if `isHoverHandlerInvoked` is true, so , invoke the exit callback.
                GestureHandler handler = hoverHandlers[currentDirection];
                if (handler!=null)
                    handler.onExit();
                isHoverHandlerInvoked = false;
            }
            //Reset state
            curHandlerStartTime = 0;
            startX = 0;
            startY = 0;
            currentDirection = GestureDirection.DIRE_NONE;
            currentDistance = 0;
        }
        return super.onTouchEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),360,360,paint);
    }

}
