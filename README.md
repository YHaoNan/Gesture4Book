## gesture 4 book

一个适合于阅读器的全面屏手势工具。使用悬浮窗+AccessibilityService的performGloablAction实现。

### 未来更新
- Touchbar透明度、边缘距离等设置持久化
- 支持手势自定义事件
- 手势处理器插件化（Lua）
- 阅读灯控制（需要阅读器厂家支持）
- 更多的手势处理器

### 2019/8/23更新
- 更新`TouchbarView`的`onTouchEvent`逻辑，手势的Hover事件更符合开发者维护手势处理器
- 更新`GestureHandler`和`BaseGestureHandler`的API
- 将手势的Hover和普通事件分开，而不是由Handler判断并处理
- 所有自带的`GestureHandler`完全重写
- 更新UI
- 权限检测&设置
- 手势处理器设置和设置持久化
- 自定义透明度，边缘距离，长度和宽度
- 优化操作 更易用

手势处理器(GestureHandler)开发规则：[在这里](./GestureHandler.md)

### 起源
最近买了个博阅的阅读器，这个阅读器很der，没有按钮（菜单、回退、HOME）。

官方也没提供类似全面屏手势的工具，自带的应用商店里有一个但是不是很好用，电纸书因为使用墨水屏所以刷新慢，市面上的全面屏手势工具的动画效果在墨水屏上会产生很大的残影，或者导致全屏刷新。很刺眼。之前找到一个挺好用的并且可以关闭动画的手势工具，但是很多功能要付费。于是我自己造了一个轮子。

目前没有UI，只实现了后退，任务列表，home，音量控制功能，并且交互不太好。
### 特性

- 体积小 没有其他功能 完全免费
- 针对墨水屏幕设计 更适合阅读器使用
- 支持使用手势完成大部分操作

### note

一共有三块区域可以触发手势，分别是屏幕左侧，屏幕右侧，屏幕底部。

支持左右上下滑动并且支持悬停功能。

能完成的操作包括：

- 回退
- 上一个应用
- home
- 任务菜单
- 音量+/-
- 打开指定应用

还有一些其他功能我觉得不常用，所以先这些

需要厂家协助的操作：

- 锁屏(Litebook的锁屏不是Android原生的)
- 阅读灯+/-
- 阅读灯模式切换
- 刷新墨水屏

