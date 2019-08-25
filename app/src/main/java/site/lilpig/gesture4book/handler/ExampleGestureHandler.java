package site.lilpig.gesture4book.handler;

import android.widget.Toast;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class ExampleGestureHandler extends BaseGestureHandler{
    public static String name="示例";
    public static int icon= R.drawable.ic_android_black_24dp;


    public ExampleGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        Toast.makeText(metaData.context, (String)getSetting("testItem"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        Toast.makeText(metaData.context, (String)getSetting("testItem"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOver() {

    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return createSettings().add(
                "testItem",
                "测试",
                String.class,
                "测试配置",
                "abc"
        ).get();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int icon() {
        return icon;
    }
}
