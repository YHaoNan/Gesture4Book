package site.lilpig.gesture4book.handler;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

import site.lilpig.gesture4book.Gesture4BookApplication;
import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.GestureService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class OpenAppGestureHandler extends BaseGestureHandler{

    public static String name = "打开应用";
    public static int icon = R.drawable.ic_android_black_24dp;

    public String currentAppName;
    public Drawable currentAppIcon;

    public OpenAppGestureHandler(String tb, String dr){
        super(tb, dr);
        String packageName = (String) getSetting("packageName");
        PackageManager packageManager = Gesture4BookApplication.getInstance().getPackageManager();
        String label = null;
        try {
            label = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName,packageManager.GET_META_DATA));
            this.currentAppName = "打开"+label;
        } catch (PackageManager.NameNotFoundException e) {
            this.currentAppName = "应用被卸载，请重新设置";
        }
    }

    public void lunchApp(Context context){
        Intent intent = Gesture4BookApplication.getInstance().getService().getPackageManager().getLaunchIntentForPackage((String) getSetting("packageName"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Gesture4BookApplication.getInstance().getService().startActivity(intent);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        lunchApp(metaData.context);
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        lunchApp(metaData.context);
    }

    @Override
    public void onOver() {

    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return createSettings().add(
                "packageName",
                "App包名",
                String.class,
                "App的包名",
                "site.lilpig.gesture4book").get();
    }

    @Override
    public String name() {
        return currentAppName!=null ? currentAppName : name;
    }

    @Override
    public int icon() {
        return icon;
    }
}
