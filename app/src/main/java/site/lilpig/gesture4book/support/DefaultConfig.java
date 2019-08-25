package site.lilpig.gesture4book.support;

import site.lilpig.gesture4book.handler.BackGestureHandler;
import site.lilpig.gesture4book.handler.HomeGestureHandler;
import site.lilpig.gesture4book.handler.NoneGestureHandler;
import site.lilpig.gesture4book.handler.RecentAppGestureHandler;
import site.lilpig.gesture4book.handler.VolumGestureHandler;

public class DefaultConfig {
    public static final Integer ALPHA = 3;
    public static final Integer LENGTH = 6;
    public static final Integer EDGE_PADDING = 0;
    public static final Integer WIDTH = 6;


    public static final String LEFT_TOP = NoneGestureHandler.class.getName();
    public static final String LEFT_TOP_HOVER = VolumGestureHandler.class.getName();
    public static final String LEFT_RIGHT = BackGestureHandler.class.getName();
    public static final String LEFT_RIGHT_HOVER = NoneGestureHandler.class.getName();
    public static final String LEFT_BOTTOM = NoneGestureHandler.class.getName();
    public static final String LEFT_BOTTOM_HOVER = VolumGestureHandler.class.getName();
    public static final String RIGHT_LEFT = BackGestureHandler.class.getName();
    public static final String RIGHT_LEFT_HOVER = NoneGestureHandler.class.getName();
    public static final String RIGHT_TOP = NoneGestureHandler.class.getName();
    public static final String RIGHT_TOP_HOVER = VolumGestureHandler.class.getName();
    public static final String RIGHT_BOTTOM = NoneGestureHandler.class.getName();
    public static final String RIGHT_BOTTOM_HOVER = VolumGestureHandler.class.getName();
    public static final String BOTTOM_LEFT = NoneGestureHandler.class.getName();
    public static final String BOTTOM_LEFT_HOVER = NoneGestureHandler.class.getName();
    public static final String BOTTOM_TOP = HomeGestureHandler.class.getName();
    public static final String BOTTOM_TOP_HOVER = RecentAppGestureHandler.class.getName();
    public static final String BOTTOM_RIGHT = NoneGestureHandler.class.getName();
    public static final String BOTTOM_RIGHT_HOVER = NoneGestureHandler.class.getName();


}
