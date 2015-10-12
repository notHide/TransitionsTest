# ViewDragHelper与Transition练习

Android的框架层新增了ViewDragHelper这么一个类，官方的DrawerLayout就是通过它来实现的。
ViewDragHelper是一个『拖拽控制器』，使用它可以用很少的代码实现很流畅的拖拽效果。Android在触摸这一块终于又进了一步。

Trasition是4.4推出的，中文名字是：过渡动画，使用它可以做出各种界面变化后的过渡效果。
Transition内部是对多个属性动画的封装，实现原理是通过记录View的初始状态和结束状态，然后通过属性动画进行播放

这个项目是自己对ViewDragHelper与Transition的一个小练习。

# 截图：  
![image](https://raw.githubusercontent.com/w9xhc/TransitionsTest/master/screenshoot/result.gif)
