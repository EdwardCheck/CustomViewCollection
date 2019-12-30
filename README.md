### android自定义view系列之必备api

写本篇博客的意图是想总结一下在实际的自定义view开发中，常被我们所用到的api方法，之所以有了这个想法，是因为自定义view写的多了，总感觉掌握的知识点越来越杂，毫无章法。所以也就有了这么一个想串串知识点的念头。本文不从概念起笔，也不教你如何实现一个view，把它简单看作一个私人的api文档就好。https://blog.csdn.net/MingJieZuo/article/details/100625718

<p align="left">
  <img width="260" height="450" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/figure_view.gif">
</p>

### android自定义view系列之仿滴滴大头针

因为考虑到完全绘制大头针会造成Ui不通用的问题，例如我们需要的效果肯定与滴滴不同，如果我将整个大头针通过draw进行绘制，那么你在移植这个view的时候，改动会很大。所以我将大头针分为了顶部圆圈view和下面的针bitmap，只通过更改自定义圆圈的大小颜色等属性来最大限度的适配Ui。

大头针的加载动画和底部波纹扩散效果，是通过内部handler定时绘制的，每次改变半径和颜色即可。view的跳动动画这里选择了AnimatorSet属性组合动画，实现起来都很简单。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/PointMarkerView.java

推荐上车点的圆点文字效果、单行双行效果以及描边效果也都是通过view绘制实现的，很常规，这里就不细说了，直接查看代码吧。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/StrokeTextView.java

<p align="left">
  <img width="260" height="450" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/spot.gif">
</p>

### android自定义view系列之圆环刻度条

此效果由view绘制实现（左下），用到了圆形、过圆心弧及文字这几种基本图形的绘制api。刻度线的绘制则是通过不断旋转canvas画布来循环画线实现的，都是比较常规的绘制方案。颜色的渐变效果，即获取每个刻度所对应的颜色段内等比例的16进制颜色值。

此view的难点是外围文字在环绕过程中，坐标位置的确认，即依圆心坐标，半径，扇形角度，如何计算出扇形终射线与圆弧交叉点的xy坐标，所幸网上都能找到解决方案及背后的数学模型。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/RingScaleView.java

可拖动环形刻度效果也由view绘制实现（左上），用到了圆弧、bitmap和文字的绘制api。刻度线的绘制则是通过不断旋转canvas画布来循环画线实现的。

拖动的实现是在规定的区域内，当手指按下，手指滑动，手指弹起时，不断绘制对应的进度p，给人一种圆环被拖着动画的错觉，其实这只是不断重绘的结果。这里需要我们通过onTouchEvent方法来监听手势及获取当前坐标。难点在于这是一个弧形轨迹，我们怎么通过当前坐标来获取角度，再根据角度获取相对应的进度。需要注意的是，在我们拖动小图标时，我们需要定一个特定的接收事件的区域范围，只有当用户按在了规定的可滑动区域内，才能让用户拖动进度条，并不是在任意位置都能拖动小图标改变进度的。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/SlideRingScaleView.java

<p align="left">
  <img width="300" height="580" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/scale.gif">
</p>

### android自定义view系列之鲸云音效

此效果是由view绘制实现（左上），主要包括波纹扩散效果、圆球旋转缩小效果及颜色渐变效果。其中波纹扩散效果，是通过定时改变波纹半径实现的，此波纹是由先后两个空心圆组成，在实现过程中要注意时间和各自的尺寸变化。

圆球效果同样是定时绘制的结果，平滑运动只是错觉。在这里是每隔200ms（波纹的定时值）在相应的位置进行绘制的，由于波纹扩散周期较短，所以我将圆球的隔旋转周期定为了45度，可根据业务自行修改。这里的难点是在于怎么找到圆球的圆心坐标， 即根据圆心坐标，半径，扇形角度来求扇形终射线与圆弧交叉点的xy坐标的问题。

圆球的不断缩小效果，也是定时改变半径进行绘制的结果，很常规，在这里就不细说了。波纹和圆球的颜色渐变效果，由于不是渐变到全透明，所以我的alpha取值范围105-255。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/MusicRippleView.java

这个效果是由四段贝塞尔曲线来拟合实现的。但这种方式出来的效果跟真正的鲸云音效（动感环绕）差别很大，所以鲸云音效不太可能是由这种方式实现的。如果有更贴近的实现方法，希望不吝赐教。博客详情：
https://mjzuo.blog.csdn.net/article/details/103762913

运动中的圆环，是不断的随机更改控制点的坐标，并为起始点添加偏移量的结果，这是一个不断调试的过程…，需要不断调整控制点来控制凸起的幅度，很难找到一个完美的效果。可查看具体代码。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/MusicBView.java

<p align="left">
  <img width="300" height="580" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/bmusic.gif">
</p>
