### Android无痕防双击框架使用方法

------

**框架目前支持以下几种，后续有需求逐步增加。**

- View的OnClick事件

- ListView的OnItemClick事件


**注意：不支持在layout的xml中定义click事件，比如：**

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

**不要在代码中定义`Onclick`、`OnItemClick`等与系统自带的各种点击事件名称相同的方法，否则，这些方法上会加上防双击代码。比如：**

```java
public void onClick(String abc) {
    Log.e("TAG",abc);
}
```


**此方案采用了ASM字节码插装方式，通过gradle的Transform阶段进行插装。由于自定义的Transform会在ProguardTransform(混淆)和DexTransform(class编译成dex文件)之前执行，所以不需要考虑混淆的情况。**