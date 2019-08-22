package site.lilpig.gesture4book.handler;

import android.view.KeyEvent;

import java.io.IOException;

import site.lilpig.gesture4book.service.KeyService;

public class BackGestureHandler extends BaseGestureHandler {

    @Override
    protected void onHover(GestureMetaData metaData) {
        KeyService.getKeyService().lockScreen();
    }

    @Override
    protected void onTrigger(GestureMetaData metaData) {
        KeyService.getKeyService().back();
    }

    @Override
    public void onOver() {

    }
}
