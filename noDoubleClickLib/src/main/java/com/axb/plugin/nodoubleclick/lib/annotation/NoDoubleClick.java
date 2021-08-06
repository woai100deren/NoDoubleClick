package com.axb.plugin.nodoubleclick.lib.annotation;

//这个注解可以用到什么地方

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
//注解保留到
//这里有三种类型，source给APT用、class给字节码插装技术用（编译后处理筛选）、RUNTIME给反射用
@Retention(RetentionPolicy.CLASS)
public @interface NoDoubleClick {
}
