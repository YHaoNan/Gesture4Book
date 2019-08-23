package site.lilpig.gesture4book.util;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import site.lilpig.gesture4book.handler.GestureHandlerSetting;

public class GestureHandlerSettingFactory {
    private final SharedPreferences s;
    List<GestureHandlerSetting> settings;
    public GestureHandlerSettingFactory(SharedPreferences sharedPreferences){
        settings = new ArrayList<>();
        this.s = sharedPreferences;
    }

    public GestureHandlerSettingFactory add(String name,String label,Class type,String description,Object defaultValue){
        GestureHandlerSetting setting = new GestureHandlerSetting(s,name,label,type,description,defaultValue);
        settings.add(setting);
        return this;
    }

    public List<GestureHandlerSetting> get(){
        return settings;
    }
}
