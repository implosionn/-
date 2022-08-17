package com.bytedance.sdk.share.demo;

import android.app.Application;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;


/**
 * 主要功能：自定义{@link Application}
 * since: 2018/12/25
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String clientkey = "awgbg6d164ck9qqr"; // 需要到开发者网站申请并替换
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));
    }
}
