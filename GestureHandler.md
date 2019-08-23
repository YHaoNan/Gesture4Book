## GestureHandler开发规范
GestureHandler是手势处理器，App中的后退，音量调节等所有手势功能都是GestureHandler。

开发者可以基于GestureHandler开发自己的手势处理器。

GestureHandler全部代码：
```java
package site.lilpig.gesture4book.handler;

import java.util.List;

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
```

直接使用这个实现类开发很麻烦，需要自己记录GestureMetadata，并在isActive中根据其中的`gestureType`返回对应的属性。并且需要在`onActive`方法中判断手势类型做相应操作。

比如自带的`VolumGestureHandler`判断了如果是悬停就每隔一段用户决定的间隔时间执行修改音量，如果是普通事件则直接修改一次。

这些代码全都自己编写的话，则每个类中都有一些重复的冗余代码，所以提供了一个`BaseGestureHandler`抽象类，它已经做好了一些事了。所以使用它开发你不用考虑太多。比如下面是返回主页的Handler。

```java
public class HomeGestureHandler extends BaseGestureHandler {

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
        return "HOME";
    }

    @Override
    public int icon() {
        return R.drawable.ic_home_black_24dp;
    }

}
```

`onTrigger`代表一次普通调用，`onHover`代表悬停，悬停松手时会回调`onOver`可以释放资源，但是`onTrigger`松手后并不会回调`onOver`，因为普通事件代表的动作是激活后立即松手，所以框架无法判断松手后`onTrigger`代码是否执行完毕，这时如果在`onOver`中释放了资源可能导致`onTrigger`中的逻辑执行异常。所以在`onTrigger`中执行完毕如果需要释放资源则需手动调用`onOver`。

下面是一个稍微复杂的案例，调节音量的Handler：
```java
public class VolumGestureHandler extends BaseGestureHandler {
    private AudioManager audioManager;

    public VolumGestureHandler(String tb, String dr) {
        super(tb, dr);
    }


    private void changeVolum(GestureMetaData metaData){
        if (audioManager == null) {
            audioManager = (AudioManager) metaData.context.getSystemService(Context.AUDIO_SERVICE);
        }
        if (metaData.direction == GestureDirection.DIRE_LEFT || metaData.direction == GestureDirection.DIRE_TOP){
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
        }else {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
        }
    }

    @Override
    public void onHover(final GestureMetaData metaData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive()==GestureType.TYPE_HOVER){
                    changeVolum(metaData);
                    try {
                        Thread.sleep((Integer) getSetting("triggerInterval"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        changeVolum(metaData);
        onExit();
    }

    @Override
    public void onOver() {
        audioManager = null;
    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return createSettings().add(
                "triggerInterval",
                "触发间隔",
                Integer.class,
                "在悬停状态下每隔多少 ·毫秒· 触发一次",
                500
        ).get();
    }

    @Override
    public String name() {
        return "音量加减";
    }

    @Override
    public int icon() {
        return R.drawable.ic_volume_up_black_24dp;
    }

}
```

## settings解析
`settings`为手势处理器和用户之间提供了一个接口，让手势处理器变得可控。`createSettings`是一个基于`Builder`模式的设置项创建器，`add`方法用于添加一个设置项，`get`方法用于返回结果（List<GestureHandlerSetting>)。

add方法的参数分别是设置项的标识（用于获取设置信息），显示在界面中的名字，类型，说明，默认值。

settings基于SharedPreference，所以只支持一些基本类型的包装类。
