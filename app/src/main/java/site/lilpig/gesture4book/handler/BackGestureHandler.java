package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.R;
import site.lilpig.gesture4book.service.KeyService;
import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

public class BackGestureHandler extends BaseGestureHandler {


    public BackGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    @Override
    public void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().back();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().back();
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
        return "返回";
    }

    @Override
    public int icon() {
        return R.drawable.ic_arrow_back_black_24dp;
    }
}
