package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class OpenAppGestureHandler extends BaseGestureHandler{
    public OpenAppGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {

    }

    @Override
    public void onTrigger(GestureMetaData metaData) {

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
        return "打开App";
    }

    @Override
    public int icon() {
        return R.drawable.ic_android_black_24dp;
    }
}
