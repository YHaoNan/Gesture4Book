package site.lilpig.gesture4book.service;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.service.autofill.OnClickAction;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import site.lilpig.gesture4book.handler.BackGestureHandler;
import site.lilpig.gesture4book.handler.GestureHandler;
import site.lilpig.gesture4book.handler.HomeGestureHandler;
import site.lilpig.gesture4book.handler.NoneGestureHandler;
import site.lilpig.gesture4book.handler.RecentAppGestureHandler;
import site.lilpig.gesture4book.handler.VolumGestureHandler;
import site.lilpig.gesture4book.ui.TouchBarHandlerSettingDialog;
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
    }



    private View.OnClickListener leftBarHandlerSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TouchBarHandlerSettingDialog dialog = new TouchBarHandlerSettingDialog(GestureService.this,leftHandlers);
            dialog.show();
        }
    };

    private View.OnClickListener rightBarHandlerSelectListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            TouchBarHandlerSettingDialog dialog = new TouchBarHandlerSettingDialog(GestureService.this,rightHandlers);
            dialog.show();
        }
    };

    private View.OnClickListener bottomBarHandlerSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TouchBarHandlerSettingDialog dialog = new TouchBarHandlerSettingDialog(GestureService.this,bottomHandlers);
            dialog.show();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initHandlers();
        createFloatTouchBar();
    }

    private void initHandlers() {
        leftHandlers = new GestureHandler[]{
                null, new NoneGestureHandler(), new BackGestureHandler("left", "right"), new NoneGestureHandler(),
                null, new VolumGestureHandler("left", "uphover"), new NoneGestureHandler(), new VolumGestureHandler("left", "downhover")
        };
        rightHandlers = new GestureHandler[]{
                new BackGestureHandler("right","left"),new NoneGestureHandler(),null,new NoneGestureHandler(),
                new NoneGestureHandler(),new VolumGestureHandler("right","uphover"),null,new VolumGestureHandler("right","downhover")
        };
        bottomHandlers = new GestureHandler[]{
                new NoneGestureHandler(),new HomeGestureHandler("bottom","up"),new NoneGestureHandler(),null,
                new NoneGestureHandler(),new RecentAppGestureHandler("bottom","uphober"),new NoneGestureHandler(),null
        };
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
        setGestureHandlerToTouchBar(leftTouchBar,leftHandlers);
        setGestureHandlerToTouchBar(rightTouchBar,rightHandlers);
        setGestureHandlerToTouchBar(bottomTouchBar,bottomHandlers);
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
    public IBinder onBind(Intent intent) {
        return new GestureServiceBinder();
    }
}
