package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class RecentAppGestureHandler extends BaseGestureHandler {

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
        return "最近应用";
    }

    @Override
    public int icon() {
        return R.drawable.ic_view_carousel_black_24dp;
    }
}
