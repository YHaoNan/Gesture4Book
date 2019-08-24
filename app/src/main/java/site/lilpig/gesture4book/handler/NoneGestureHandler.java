package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class NoneGestureHandler implements GestureHandler{

    @Override
    public void onActive(GestureMetaData metaData) {

    }

    @Override
    public void onExit() {

    }

    @Override
    public int isActive() {
        return 0;
    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return null;
    }

    @Override
    public String name() {
        return "无";
    }

    @Override
    public int icon() {
        return R.drawable.ic_do_not_disturb_alt_black_24dp;
    }
}
