### Android无痕防双击框架使用方法

------

**<font color=Red>注意：不支持Lambda写法，比如下面写法：</font>**

```java
findViewById(R.id.button2).setOnClickListener(v -> {
    Log.e("TAG","button2被点击了："+System.currentTimeMillis());
});
```