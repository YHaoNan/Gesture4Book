package site.lilpig.gesture4book.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;

import site.lilpig.gesture4book.handler.BackGestureHandler;
import site.lilpig.gesture4book.handler.HomeGestureHandler;
import site.lilpig.gesture4book.handler.VolumGestureHandler;
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

    private int edgePadding;
    private int barWidth;
    private int barColor;
    private int barSidePadding;
    public class GestureServiceBinder extends Binder {
        public void setEdgePadding(int edgePadding){
            GestureService.this.edgePadding = edgePadding;
        }
        public void setBarWidth(int barWidth){
            GestureService.this.barWidth = barWidth;
        }
        public void setBarColor(int barColor){
            GestureService.this.barColor = barColor;
        }
        public void setBarSidePadding(int barSidePadding){
            GestureService.this.barSidePadding = barSidePadding;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        edgePadding = DisplayUtil.dip2px(this,0);
        barWidth = DisplayUtil.dip2px(this,30);
        barColor = 0x30000000;
        barSidePadding = DisplayUtil.dip2px(this,200);
        createFloatTouchBar();

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
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wParams.gravity = gravity;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wParams.x = x;
        wParams.y = y;

        //设置悬浮窗口长宽数据
        wParams.width = w;
        wParams.height = h;
        return wParams;
    }
    private void createFloatTouchBar(){
        deviceWH = DisplayUtil.getDeviceWH(this);
        wmanager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        leftTouchBar = new TouchBarView(getApplicationContext());
        leftParams = initLayoutParams(Gravity.LEFT|Gravity.TOP , edgePadding,barSidePadding, barWidth,deviceWH[1]-barSidePadding*2);
        rightTouchBar = new TouchBarView(getApplicationContext());
        rightParams = initLayoutParams(Gravity.LEFT|Gravity.TOP,deviceWH[0]-edgePadding-barWidth,barSidePadding,barWidth,deviceWH[1]-barSidePadding*2);
        bottomTouchBar = new TouchBarView(getApplicationContext());
        bottomParams = initLayoutParams(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,edgePadding,deviceWH[0]-barSidePadding*2,barWidth);
        leftTouchBar.setTopHandler(new VolumGestureHandler());
        leftTouchBar.setBottomHandler(new VolumGestureHandler());
        leftTouchBar.setRightHandler(new BackGestureHandler());
        rightTouchBar.setTopHandler(new VolumGestureHandler());
        rightTouchBar.setBottomHandler(new VolumGestureHandler());
        rightTouchBar.setLeftHandler(new BackGestureHandler());
        bottomTouchBar.setTopHandler(new HomeGestureHandler());
        wmanager.addView(leftTouchBar,leftParams);
        wmanager.addView(rightTouchBar,rightParams);
        wmanager.addView(bottomTouchBar,bottomParams);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new GestureServiceBinder();
    }
}
