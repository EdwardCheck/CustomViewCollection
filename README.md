
### android自定义view系列之必备api

写本篇博客的意图是想总结一下在实际的自定义view开发中，常被我们所用到的api方法，之所以有了这个想法，是因为自定义view写的多了，总感觉掌握的知识点越来越杂，毫无章法。所以也就有了这么一个想串串知识点的念头。本文不从概念起笔，也不教你如何实现一个view，把它简单看作一个私人的api文档就好。https://blog.csdn.net/MingJieZuo/article/details/100625718

<p align="center">
  <img width="260" height="450" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/figure_view.gif">
</p>

### android自定义view系列之仿滴滴大头针

因为考虑到完全绘制大头针会造成Ui不通用的问题，例如我们需要的效果肯定与滴滴不同，如果我将整个大头针通过draw进行绘制，那么你在移植这个view的时候，改动会很大。所以我将大头针分为了顶部圆圈view和下面的针bitmap，只通过更改自定义圆圈的大小颜色等属性来最大限度的适配Ui。

大头针的加载动画和底部波纹扩散效果，是通过内部handler定时绘制的，每次改变半径和颜色即可。view的跳动动画这里选择了AnimatorSet属性组合动画，实现起来都很简单。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/PointMarkerView.java

推荐上车点的圆点文字效果、单行双行效果以及描边效果也都是通过view绘制实现的，很常规，这里就不细说了，直接查看代码吧。
https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/java/com/mjzuo/views/view/StrokeTextView.java

<p align="center">
  <img width="260" height="450" src="https://github.com/MingJieZuo/CustomViewCollection/blob/master/app/src/main/assets/imgfolder/spot.gif">
</p>
