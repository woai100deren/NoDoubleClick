### Android无痕防双击框架使用方法

------

一、使用：

- 工程`build.gradle`中引入：

```groovy
buildscript {
    repositories {
    	......
    }
    dependencies {
        ......
        classpath "com.axb.plugin:noDoubleClickPugin:1.0.0"
    }
}

allprojects {
    repositories {
    	......
        jcenter()
        //引入发布到jCenter的在线maven仓库
        maven{
            url uri('https://dl.bintray.com/woai100deren/NoDoubleClick')
        }
    }
}
......
```

- 要使用到的module中的`build.gradle`引入

```groovy
plugins {
    ......
    id 'com.axb.plugin.noDoubleClick'
}

或者

apply plugin 'com.axb.plugin.noDoubleClick'

......

//引入工具类
dependencies {
	implementation 'com.axb.plugin:noDoubleClickLib:1.0.0'
}
```

**二、框架目前支持以下几种，后续有需求逐步增加。**

- View的OnClick事件
- ListView的OnItemClick事件

**三、说明：**

- 此方案采用了ASM字节码插装方式，通过gradle的Transform阶段进行插装。由于自定义的Transform会在ProguardTransform(混淆)和DexTransform(class编译成dex文件)之前执行，所以不需要考虑混淆的情况。

- 此方案支持Lambda写法，如：

  ```java
  findViewById(R.id.button).setOnClickListener(v -> {
      Log.e("TAG","button被点击了："+System.currentTimeMillis());
  });
  ```

**四、原理：**

- 本质上，是在你的代码基础上，在外层包裹了一层判断。比如将上述Lambda写法最终改变成了如下：

  ```java
  findViewById(R.id.button2).setOnClickListener(v -> {
      if(!NoDoubleClickUtil.isOnDoubleClick()){//------------这一行是增加的代码
          //这里会将你写的所有代码逻辑全部包裹进来
      	Log.e("TAG","button2被点击了："+System.currentTimeMillis());
      }//------------这一行是增加的代码
  });
  ```

**五、注意：**

- 不支持在layout的xml中定义click事件，比如：

```xml
<Button
    android:id="@+id/button1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="test"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent" 
    android:onClick="aaa"/>
```

- 不要在代码中定义`Onclick`、`OnItemClick`等与系统自带的各种点击事件名称相同的方法，否则，这些方法上会加上防双击代码。比如：

```java
public void onClick(String abc) {
    Log.e("TAG",abc);
}
```


