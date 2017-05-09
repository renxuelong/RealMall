package com.renxl.realmall.http;

import com.renxl.realmall.BuildConfig;

/**
 * Created by renxl
 * On 2017/3/30 20:23.
 */

public class RealMallClient extends BaseClient {

    private static final String QA = "http://112.124.22.238:8081"; // 测试环境域名
    private static final String PRODUCTION = "http://112.124.22.238:8081"; // 生产环境域名

    public static String getBaseUrl() {
        return BuildConfig.DEBUG ? QA : PRODUCTION;
    }
    public String getHost() {
        return BuildConfig.DEBUG ? QA : PRODUCTION;
    }

    private static RealMallClient realMallClient = new RealMallClient();

    private RealMallClient() {
    }

    public static RealMallClient getInstance() {
        return realMallClient;
    }
}
