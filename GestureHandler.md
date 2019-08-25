## GestureHandler开发规范
开发者通过创建`BaseGesutreHandler`的子类来编写手势处理器。

一个空的手势处理器大概是这样：
```java
public class ExampleGestureHandler extends BaseGestureHandler{
    public static String name = "示例";
    public static int icon = R.drawble.test;
    //用于确认手势被设置的位置，BaseGestureHandler会处理，一般来说用户不用在意这个构造器
    public ExampleGestureHandler(String tb, String dr) {
        super(tb, dr);
    }

    //Hover事件 
    @Override
    public void onHover(GestureMetaData metaData) {

    }
    //Trigger事件
    @Override
    public void onTrigger(GestureMetaData metaData) {

    }

    //Over事件
    @Override
    public void onOver() {

    }

    //处理器设置项
    @Override
    public List<GestureHandlerSetting> settings() {
        return null;
    }

    //处理器名字
    @Override
    public String name() {
        return name;
    }

    //处理器图标
    @Override
    public int icon() {
        return icon;
    }
}
```

## static属性
每一个处理器类中都必须包含两个公开的静态属性，`name`和`icon`，用于确定处理器名称和图标。

因为只有当用户选中这个处理器时处理器才会被实例化，所以当用户选择处理器时就会读取这两个静态的，当用户选择完毕后读取的是`name`和`icon`实例方法。这样设计的好处是让实例化后的icon和name可以表现出不同的行为。


比如打开应用的手势处理器，静态name属性为`打开App`，也就是说用户选择时看到的名字是`打开App`，而实例化后的name方法返回的则是`"打开"+currentAppName`就是例如`打开QQ`这种效果。

## GestureMetaData
这是用户触发手势的元数据。

它有如下四个属性

属性|描述
-:|:-:
public final Context context|服务上下文
public final int gestureType|手势类型，分为悬浮和普通
public final float distance|用户滑动的距离(在目前版本中已经没有用了)
public final int direction|方向

如果你开发的手势处理器对这些数据敏感，可以通过传入的`GestureMetaData`获取，比如自带的音量处理插件就会判断方向，如果是上或左就加大声音，否则减小声音。
## 处理器事件
处理器有三个事件，分别是`onHover`、`onTrigger`和`onOver`，它们代表三个用户动作，分别是滑动按住，滑动立即松手和松手。

为什么有两个松手呢？先说明`onOver`对应的是`onHover`，也就是说，当用户触发手势并按住时触发`onHover`，用户松手时触发`onOver`。而`onTrigger`代表的就是滑动立即松手的动作，也就是说当它被触发时用户已经松手了，所以它没有对应的结束事件。

这样设计是有原因的，因为`Hover`经常被用于做一些持续性的动作，比如加大音量，用户只要不松手就要一直持续加大，但是总要有个停止的时候，所以就单独设计了`onOver`告诉处理器用户松手了。

onHover中如果要处理持续性逻辑的话请使用子线程。

如果你的处理器对hover和trigger要表现出不同的反应，并且都需要释放资源，推荐在trigger的最后一行手动调用`onOver`，并在onOver里释放。

## 用户设置项
用户可以对手势处理器进行配置，如果你的手势处理器需要配置的话，请在`settings`方法中返回配置，并使用`getSetting`方法获取设置项。

比如：
```java
public List<GestureHandlerSetting> settings() {
    return createSettings().add(
        "testItem",
        "测试",
        String.class,
        "测试配置",
        "abc"
    ).get();
}
```
createSettings基于Builder模式可以快速的创建设置项，`add`方法添加一个设置项，你需要传入设置项标识，设置项名字，设置项值类型，设置项说明，设置项默认值，并可以一直连续的调用`add`，最后使用`get`获取设置项并返回。

然后用户会看到这样的设置界面：

![图片](http://nsimg.cn-bj.ufileos.com/img-1566700759326.jpg)

然后你可以这样使用设置项：
```java
public class ExampleGestureHandler extends BaseGestureHandler{
    @Override
    public void onHover(GestureMetaData metaData) {
        Toast.makeText(metaData.context, (String)getSetting("testItem"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrigger(GestureMetaData metaData) {
        Toast.makeText(metaData.context, (String)getSetting("testItem"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<GestureHandlerSetting> settings() {
        return createSettings().add(
                "testItem",
                "测试",
                String.class,
                "测试配置",
                "abc"
        ).get();
    }
}
```

用户触发手势后会弹出提示：

![图片](http://nsimg.cn-bj.ufileos.com/img-1566700784121.jpg)

## 示例
目前所有的手势处理器都在[这个包下](./app/src/main/java/site/lilpig/gesture4book/handler)，可以随意参考。

