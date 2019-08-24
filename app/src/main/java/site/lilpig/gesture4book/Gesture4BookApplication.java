package site.lilpig.gesture4book;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.List;

import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;

public class Gesture4BookApplication extends Application {

    private List<GestureHandlerSetting> currentSetting = null;
    private static Gesture4BookApplication instance;

    private GestureService service;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public SharedPreferences openSharedPreference(String tb, String dr, String handlerName){
        return getSharedPreferences(tb+"$"+dr+"$"+handlerName,MODE_PRIVATE);
    }

    public void setCurrentSetting(List<GestureHandlerSetting> currentSetting) {
        this.currentSetting = currentSetting;
    }

    public void setService(GestureService service){
        this.service = service;
    }

    public GestureService getService() {
        return service;
    }

    public List<GestureHandlerSetting> getCurrentSetting() {
        return currentSetting;
    }

    public static Gesture4BookApplication getInstance() {
        return instance;
    }
}
