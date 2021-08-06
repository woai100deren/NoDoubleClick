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
        //本地模式下的maven仓库
        maven {
            url uri("$rootDir/repo")
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

- 使用方法：在需要防双击的方法上，加上`@NoDoubleClick`注解

```java
findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
    @NoDoubleClick
    @Override
    public void onClick(View v) {
        Log.e("TAG","button1被点击了："+System.currentTimeMillis());
    }
});
```

**二、框架目前支持以下几种，后续有需求逐步增加。**

- View的OnClick事件
- ListView的OnItemClick事件

**三、说明：**

- 此方案采用了ASM字节码插装方式，通过gradle的Transform阶段进行插装。由于自定义的Transform会在ProguardTransform(混淆)和DexTransform(class编译成dex文件)之前执行，所以不需要考虑混淆的情况。

- 此方案不支持Lambda写法，因为无法加注解，如：

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
