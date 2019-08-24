package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class HomeGestureHandler extends BaseGestureHandler {
    public static String name = "HOME";
    public static int icon = R.drawable.ic_home_black_24dp;

    public HomeGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().home();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().home();
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
