package site.lilpig.gesture4book.handler;

import android.accessibilityservice.AccessibilityService;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.List;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;

public class HomeGestureHandler extends BaseGestureHandler {

    public HomeGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().home();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().home();
    }

    @Override
    public void onOver() {
    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return null;
    }

    @Override
    public String name() {
        return "HOME";
    }

    @Override
    public int icon() {
        return R.drawable.ic_home_black_24dp;
    }

}
