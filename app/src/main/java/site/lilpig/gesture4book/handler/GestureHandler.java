package site.lilpig.gesture4book.handler;

import java.util.List;

import site.lilpig.gesture4book.support.GestureHandlerSetting;
import site.lilpig.gesture4book.support.GestureMetaData;

/**
 * Programmer can implement a handler to handle a gesture operate.
 * A handler can launch a application,  controll the system volume...
 */
public interface GestureHandler {
    /**
     * It will be called when a specific gesture triggered.
     * You can implement the handler logic in this method. Such as , launch a application.
     * @param metaData GestureMetaData
     */
    void onActive(GestureMetaData metaData);

    /**
     * It will be called when the user release the finger in HOVER mode.
     * In this function you can release resource. Wait for the next called.
     */
    void onExit();

    /**
     * Return the gestureType if onActive is triggered. Otherwise return -1;
     * @return gestureType
     */
    int isActive();

    /**
     * Settings
     * @return
     */
    List<GestureHandlerSetting> settings();

    String name();

    int icon();

}
