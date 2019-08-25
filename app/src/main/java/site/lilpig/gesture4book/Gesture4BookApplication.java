package site.lilpig.gesture4book;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.List;

import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;

public class Gesture4BookApplication extends Application {

    private SharedPreferences appConfig;
    private List<GestureHandlerSetting> currentSetting = null;
    private static Gesture4BookApplication instance;

    private GestureService service;


    @Override
    public void onCreate() {
        super.onCreate();
        appConfig = getSharedPreferences("gesture4book_config",MODE_PRIVATE);
        instance = this;
    }



    public SharedPreferences getSettingSharedPreferenceByTouchbarAndDirection(String tb, String dr, String handlerName){
        return getSharedPreferences(tb+"$"+dr+"$"+handlerName,MODE_PRIVATE);
    }

    public SharedPreferences getAppConfig() {
        return appConfig;
    }

    public void setCurrentSetting(List<GestureHandlerSetting> currentSetting) {
        this.currentSetting = currentSetting;
    }
    public List<GestureHandlerSetting> getCurrentSetting() {
        return currentSetting;
    }

    //用于将Service设置到全局
    public void setService(GestureService service){
        this.service = service;
    }
    public GestureService getService() {
        return service;
    }

    public static Gesture4BookApplication getInstance() {
        return instance;
    }
}
