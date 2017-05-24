package com.fupengpeng.butterknife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ButterKnife 用法说明
 *     1.model的build.grade添加依赖：
 *         apt 'com.jakewharton:butterknife-compiler:8.1.0'//增加这一句
 *     2.model的build.grade下添加
 *         apply plugin: 'com.neenbedankt.android-apt'//增加这一句
 *     3.工程项目build.grade添加依赖
 *         classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'.
 *     4.具体用法详见代码中
 *     butterknife zelezny
 */
public class ButterKnifeActivity extends AppCompatActivity {
    /**
     * ButterKnife解绑对象
     */
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        //ButterKnife绑定ButterKnifeActivity
        unbinder = ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        unbinder.unbind();
    }
}
