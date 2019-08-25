package site.lilpig.gesture4book.handler;

public class BuiltInHandlers {
    public static Class<GestureHandler>[] handlers = new Class[]{
            NoneGestureHandler.class,
            BackGestureHandler.class,
            HomeGestureHandler.class,
            RecentAppGestureHandler.class,
            VolumGestureHandler.class,
            NotificationsGestureHandler.class,
            ExampleGestureHandler.class
    };
}
