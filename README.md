# LinkedScrollDemo
## CSDN 链接
http://blog.csdn.net/a153614131/article/details/52834415

## 一个仿饿了么订餐页面的双列表联动Demo

先上图吧，这样的订单页面是不是很眼熟：

![这里写图片描述](http://img.blog.csdn.net/20161016232906229)

右边的listview分好组以后，在左边的Tab页建立索引。可以直接导航，是不是很方便。

趁着周末，我也撸一个。我称其为LinkedLayout，看下效果图：

![这里写图片描述](http://img.blog.csdn.net/20161016234308785)

我把右边按5个一组，可以看到，左边的索引 ＝ 右边／5
<br/>

## 特点：
- 右边滑动，左边跟着动
- 左边滑动到边界，右边跟着动
- 点击左边tab项，右边滑动定位到相应的group

## 知识点
我们能从这个demo里收获到什么:

- 面向抽象／接口编程
- 自定义 view
- 代理模式
- 复习 listview && recyclerview 的细节
