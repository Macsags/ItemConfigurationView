# ItemConfigurationView

简介：
-------
条目配置、设置配置、 类似设置界面条目控件
配置头图片、title 、 edittext、 尾单选框 、多选控 、 switch 、 单位 等等
</br>
****
示例：
-------
![](https://imgm.gmw.cn/attachement/jpg/site215/20210111/5618409004324637012.jpg)

[图片地址](https://img-blog.csdnimg.cn/20200824180331844.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMyMzY4MTI5,size_16,color_FFFFFF,t_70)
</br>
****
日志
-------
2021/1/7
* 第一次上传
****
如何使用How to：
-------
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

```
allprojects { 
		repositories { 
			... 
			maven { url 'https://www.jitpack.io' } 
		} 
	}  		
```

Step 2. Add the dependency<br> 

```
allprojects { 
	dependencies {
	          implementation 'com.github.Macsags:ItemConfigurationView:1.0.1'
	      } 
	} 
```	
****
使用方法：
-------
```
    XML里加自定义view
      //图1
      <com.macsags.itemconfigure.ItemConfigureView
        android:id="@+id/ic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:leftIcon = "@mipmap/gerenxinxi"
        app:leftText="个人信息"
        app:rightStyle="iconClick"
        app:textColor="@color/black_333333" />
	//图2
	<com.macsags.itemconfigure.ItemConfigureView
        android:id="@+id/ic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:leftIcon = "@mipmap/gerenxinxi"
        app:leftText="姓名"
        app:rightText ="张三"
        app:rightStyle="iconGone"
        app:textColor="@color/black_333333" />
	//图3
	<com.macsags.itemconfigure.ItemConfigureView
        android:id="@+id/ic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:leftIcon = "@mipmap/gerenxinxi"
        app:leftText="张三的铅笔"
        app:rightText ="3"
        app:rightIconText ="支"
        app:rightStyle="iconText"
        app:textColor="@color/black_333333" />
	//图4
	<com.macsags.itemconfigure.ItemConfigureView
        android:id="@+id/ic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:leftIcon = "@mipmap/gerenxinxi"
        app:leftText="值日生"
        app:rightText ="张三"
        app:rightStyle="iconCheck"
        app:textColor="@color/black_333333" />
	//图5
	<com.macsags.itemconfigure.ItemConfigureView
        android:id="@+id/ic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:leftIcon = "@mipmap/gerenxinxi"
        app:leftText="飞行模式"
        app:rightStyle="iconSwitch"
        app:textColor="@color/black_333333" />
       
```
****
请关注
-------
  [我的博客](https://blog.csdn.net/qq_32368129)
  
