package site.lilpig.gesture4book.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.handler.BackGestureHandler;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.handler.HomeGestureHandler;
import site.lilpig.gesture4book.handler.NoneGestureHandler;
import site.lilpig.gesture4book.handler.NotificationsGestureHandler;
import site.lilpig.gesture4book.handler.RecentAppGestureHandler;
import site.lilpig.gesture4book.handler.VolumGestureHandler;
import site.lilpig.gesture4book.ui.GestureHandlerSelectDialog;
import site.lilpig.gesture4book.util.ConfigUtil;
import site.lilpig.gesture4book.util.DisplayUtil;
import site.lilpig.gesture4book.view.TouchBarView;

public class GestureService extends Service {

    private int[] deviceWH;

    private TouchBarView leftTouchBar;
    private TouchBarView rightTouchBar;
    private TouchBarView bottomTouchBar;

    private WindowManager.LayoutParams leftParams;
    private WindowManager.LayoutParams rightParams;
    private WindowManager.LayoutParams bottomParams;
    private WindowManager wmanager;

    GestureHandler[] leftHandlers = new GestureHandler[8];
    GestureHandler[] rightHandlers = new GestureHandler[8];
    GestureHandler[] bottomHandlers = new GestureHandler[8];

    private boolean handlerInited = false;
    public static final String[] DRS = {
            "left","top","right","bottom","lefthover","tophover","righthover","bottomhover"
    };

    public class GestureServiceBinder extends Binder {
        public void setEdgePadding(int edgePadding){
            GestureService.this.setEdgePadding(edgePadding);
        }
        public void setBarWidth(int barWidth){
            GestureService.this.setBarWidth(barWidth);
        }
        public void setBarAlpha(int barAlpha){
            rightTouchBar.setAlpha(barAlpha);
            leftTouchBar.setAlpha(barAlpha);
            bottomTouchBar.setAlpha(barAlpha);
        }
        public void setBarSidePadding(int barSidePadding){
            GestureService.this.setBarSidePadding(barSidePadding);
        }

        public void bindLeftListener(Button button){
            button.setOnClickListener(leftBarHandlerSelectListener);
        }

        public void bindRightListener(Button button){
            button.setOnClickListener(rightBarHandlerSelectListener);
        }

        public void bindBottomListener(Button button){
            button.setOnClickListener(bottomBarHandlerSelectListener);
        }
        public void saveGestureStateHandler(){
            GestureService.this.saveGestureHandlerState();
        }
    }



    private View.OnClickListener leftBarHandlerSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GestureHandlerSelectDialog dialog = new GestureHandlerSelectDialog("left",GestureService.this,leftHandlers);
            dialog.setTitle("左侧操作");
            dialog.show();
        }
    };

    private View.OnClickListener rightBarHandlerSelectListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            GestureHandlerSelectDialog dialog = new GestureHandlerSelectDialog("right",GestureService.this,rightHandlers);
            dialog.setTitle("右侧操作");
            dialog.show();
        }
    };

    private View.OnClickListener bottomBarHandlerSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GestureHandlerSelectDialog dialog = new GestureHandlerSelectDialog("bottom",GestureService.this,bottomHandlers);
            dialog.setTitle("底部操作");
            dialog.show();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Gesture4BookApplication.getInstance().setService(this);
        createFloatTouchBar();
        initHandlers();
    }

    private void initHandlers() {
        setHandler(1,"left",ConfigUtil.getLeftTop());
        setHandler(5,"left",ConfigUtil.getLeftTopHover());
        setHandler(2,"left",ConfigUtil.getLeftRight());
        setHandler(6,"left",ConfigUtil.getLeftRightHover());
        setHandler(3,"left",ConfigUtil.getLeftBottom());
        setHandler(7,"left",ConfigUtil.getLeftBottomHover());
        setHandler(0,"right",ConfigUtil.getRightLeft());
        setHandler(4,"right",ConfigUtil.getRightLeftHover());
        setHandler(1,"right",ConfigUtil.getRightTop());
        setHandler(5,"right",ConfigUtil.getRightTopHover());
        setHandler(3,"right",ConfigUtil.getRightBottom());
        setHandler(7,"right",ConfigUtil.getRightBottomHover());
        setHandler(0,"bottom",ConfigUtil.getBottomLeft());
        setHandler(4,"bottom",ConfigUtil.getBottomLeftHover());
        setHandler(1,"bottom",ConfigUtil.getBottomTop());
        setHandler(5,"bottom",ConfigUtil.getBottomTopHover());
        setHandler(2,"bottom",ConfigUtil.getBottomRight());
        setHandler(6,"bottom",ConfigUtil.getBottomRightHover());
        handlerInited = true;
    }

    public boolean setHandler(int index,String tb,String clzName){
        try {
            return setHandler(index,tb, (Class<GestureHandler>) Class.forName(clzName));
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "找不到处理器："+clzName, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean setHandler(int index,String tb,Class<GestureHandler> handlerClass){
        try {
            GestureHandler handler = handlerClass.getConstructor(String.class,String.class).newInstance(tb,DRS[index]);
            return setHandler(index,tb,handler);
        }catch (Exception e){
            Toast.makeText(this, "修改失败："+e.getMessage()+",index "+ index+",tb "+tb+", handlerClass "+handlerClass.getSimpleName(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }

    public boolean setHandler(int dr, String tb, GestureHandler handler){
        if (tb.equals("left"))
            leftHandlers[dr] = handler;
        else if (tb.equals("right"))
            rightHandlers[dr] = handler;
        else if (tb.equals("bottom"))
            bottomHandlers[dr] = handler;
        else
            return false;
        updateTouchBarHandlers();
        return true;
    }

    private WindowManager.LayoutParams initLayoutParams(int gravity, int x, int y, int w, int h){
        WindowManager.LayoutParams wParams = new WindowManager.LayoutParams();
        wParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wParams.format = PixelFormat.RGBA_8888;
        wParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wParams.gravity = gravity;
        wParams.x = x;
        wParams.y = y;
        wParams.width = w;
        wParams.height = h;
        return wParams;
    }
    private void createFloatTouchBar(){
        deviceWH = DisplayUtil.getDeviceWH(this);
        wmanager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        leftTouchBar = new TouchBarView(getApplicationContext());
        leftParams = initLayoutParams(Gravity.LEFT|Gravity.CENTER_VERTICAL , 0,0,0,0);
        rightTouchBar = new TouchBarView(getApplicationContext());
        rightParams = initLayoutParams(Gravity.RIGHT|Gravity.CENTER_VERTICAL,0,0,0,0);
        bottomTouchBar = new TouchBarView(getApplicationContext());
        bottomParams = initLayoutParams(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0,0,0);
        leftTouchBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                leftBarHandlerSelectListener.onClick(view);
                return false;
            }
        });

        rightTouchBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                rightBarHandlerSelectListener.onClick(view);
                return false;
            }
        });

        bottomTouchBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                bottomBarHandlerSelectListener.onClick(view);
                return false;
            }
        });
        wmanager.addView(leftTouchBar,leftParams);
        wmanager.addView(rightTouchBar,rightParams);
        wmanager.addView(bottomTouchBar,bottomParams);
    }


    private void setGestureHandlerToTouchBar(TouchBarView view,GestureHandler[] handlers){
        view.setLeftHandler(handlers[0]);
        view.setTopHandler(handlers[1]);
        view.setRightHandler(handlers[2]);
        view.setBottomHandler(handlers[3]);
        view.setLeftHoverHandler(handlers[4]);
        view.setTopHoverHandler(handlers[5]);
        view.setRightHoverHandler(handlers[6]);
        view.setBottomHoverHandler(handlers[7]);
        if (handlerInited)
            saveGestureHandlerState();
    }

    public void updateTouchBarHandlers(){
        setGestureHandlerToTouchBar(leftTouchBar,leftHandlers);
        setGestureHandlerToTouchBar(rightTouchBar,rightHandlers);
        setGestureHandlerToTouchBar(bottomTouchBar,bottomHandlers);
    }


    public void setEdgePadding(int padding){
        padding = DisplayUtil.dip2px(getApplicationContext(),padding);
        leftParams.x = padding;
        rightParams.x = padding;
        bottomParams.y = padding ;
        updateViewLayout();
    }

    public void setBarWidth(int barWidth){
        barWidth = DisplayUtil.dip2px(getApplicationContext(),barWidth);
        leftParams.width = barWidth;
        rightParams.width = barWidth;
        bottomParams.height = barWidth;
        updateViewLayout();
    }


    public void setBarSidePadding(int padding){
        padding = DisplayUtil.dip2px(getApplicationContext(),padding);
        leftParams.height = deviceWH[1]-padding*2;
        rightParams.height = deviceWH[1]-padding*2;
        bottomParams.width = deviceWH[0]-padding*2;
        updateViewLayout();
    }
    public void updateViewLayout(){
        wmanager.updateViewLayout(leftTouchBar,leftParams);
        wmanager.updateViewLayout(rightTouchBar,rightParams);
        wmanager.updateViewLayout(bottomTouchBar,bottomParams);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        wmanager.removeView(leftTouchBar);
        wmanager.removeView(rightTouchBar);
        wmanager.removeView(bottomTouchBar);
        return super.onUnbind(intent);
    }

    public void saveGestureHandlerState(){
        ConfigUtil.setLeftTop(leftHandlers[1].getClass().getName());
        ConfigUtil.setLeftTopHover(leftHandlers[5].getClass().getName());
        ConfigUtil.setLeftRight(leftHandlers[2].getClass().getName());
        ConfigUtil.setLeftRightHover(leftHandlers[6].getClass().getName());
        ConfigUtil.setLeftBottom(leftHandlers[3].getClass().getName());
        ConfigUtil.setLeftBottomHover(leftHandlers[7].getClass().getName());
        ConfigUtil.setRightLeft(rightHandlers[0].getClass().getName());
        ConfigUtil.setRightLeftHover(rightHandlers[4].getClass().getName());
        ConfigUtil.setRightTop(rightHandlers[1].getClass().getName());
        ConfigUtil.setRightTopHover(rightHandlers[5].getClass().getName());
        ConfigUtil.setRightBottom(rightHandlers[3].getClass().getName());
        ConfigUtil.setRightBottomHover(rightHandlers[7].getClass().getName());
        ConfigUtil.setBottomLeft(bottomHandlers[0].getClass().getName());
        ConfigUtil.setBottomLeftHover(bottomHandlers[4].getClass().getName());
        ConfigUtil.setBottomTop(bottomHandlers[1].getClass().getName());
        ConfigUtil.setBottomTopHover(bottomHandlers[5].getClass().getName());
        ConfigUtil.setBottomRight(bottomHandlers[2].getClass().getName());
        ConfigUtil.setBottomRightHover(bottomHandlers[6].getClass().getName());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new GestureServiceBinder();
    }
}
