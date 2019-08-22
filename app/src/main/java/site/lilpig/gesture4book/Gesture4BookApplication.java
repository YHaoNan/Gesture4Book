package site.lilpig.gesture4book;

import android.accessibilityservice.AccessibilityService;
import android.app.Application;

import site.lilpig.gesture4book.handler.GestureType;

public class Gesture4BookApplication extends Application {
    private static Gesture4BookApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static Gesture4BookApplication getInstance() {
        return instance;
    }
}
