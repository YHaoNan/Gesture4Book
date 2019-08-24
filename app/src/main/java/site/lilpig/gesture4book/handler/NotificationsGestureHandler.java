package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class NotificationsGestureHandler extends BaseGestureHandler{

    public static String name = "打开通知";
    public static int icon = R.drawable.ic_textsms_black_24dp;

    public NotificationsGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().notifications();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().notifications();

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
