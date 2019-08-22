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

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.service.KeyService;

public class HomeGestureHandler extends BaseGestureHandler {

    private boolean isHovered = false;
    @Override
    protected void onHover(GestureMetaData metaData) {
        if (isHovered)return;
        isHovered = true;
        KeyService.getKeyService().recentApp();
    }

    @Override
    protected void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().home();
    }

    @Override
    public void onOver() {
        isHovered = false;
    }
}
