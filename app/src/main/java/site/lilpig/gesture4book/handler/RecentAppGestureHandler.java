package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class RecentAppGestureHandler extends BaseGestureHandler {

    public static String name = "最近应用";
    public static int icon = R.drawable.ic_view_carousel_black_24dp;

    public RecentAppGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().recentApp();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().recentApp();
    }

    @Override
    public void onOver() {

    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return null;
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
