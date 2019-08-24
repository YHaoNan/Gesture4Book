package site.lilpig.gesture4book.handler;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;
import site.lilpig.gesture4book.support.GestureType;
import site.lilpig.gesture4book.util.GestureHandlerSettingFactory;

public abstract class BaseGestureHandler implements GestureHandler{
    private Map<String, GestureHandlerSetting> settingMap;
    private GestureMetaData metaData;
    private SharedPreferences userSettingsFile;
    public BaseGestureHandler(String tb,String dr){
        userSettingsFile = Gesture4BookApplication.getInstance().openSharedPreference(tb,dr,this.getClass().getName());
        settingMap = new HashMap<>();
        if (settings()!=null)
            for (GestureHandlerSetting setting:settings()){
                settingMap.put(setting.getName(),setting);
            }
    }

    @Override
    public void onActive(GestureMetaData metaData) {
        this.metaData = metaData;
        if (metaData.gestureType == GestureType.TYPE_HOVER){
            onHover(metaData);
        }else{
            onTriggerWrapper(metaData);
        }
    }

    protected void onTriggerWrapper(GestureMetaData metaData){
        onTrigger(metaData);
        onExit();
    }

    public abstract void onHover(GestureMetaData metaData);

    public abstract void onTrigger(GestureMetaData metaData);

    @Override
    public void onExit() {
        this.metaData = null;
        onOver();
    }

    @Override
    public int isActive() {
        return this.metaData != null ? this.metaData.gestureType : -1;
    }

    public abstract void onOver();

    protected GestureHandlerSettingFactory createSettings(){
        return new GestureHandlerSettingFactory(userSettingsFile);
    }

    protected Object getSetting(String name){
        return settingMap.get(name).getValue();
    }

}
