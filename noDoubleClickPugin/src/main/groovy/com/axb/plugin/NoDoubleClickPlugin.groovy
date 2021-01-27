package com.axb.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 防双击插件
 */
public class NoDoubleClickPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension.class)
        NoDoubleClickTransform transform = new NoDoubleClickTransform()
        android.registerTransform(transform)
    }
}