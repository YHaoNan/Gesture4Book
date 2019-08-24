package site.lilpig.gesture4book.support;

import site.lilpig.gesture4book.handler.GestureHandler;

public interface OnGestureHandlerSelectedListener {
    void onSelect(Class<GestureHandler> handlerClass,String ...args);

}
