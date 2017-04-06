package com.renxl.realmall.other;

/**
 * Created by renxl
 * On 2017/3/29 17:37.
 */

public class Test {
    public static final String cookie = "SINAGLOBAL=8175583028407.576.1487907254415; un=15632279639; wvr=6; SCF=AmB08oIUpYwQlC14gNU9wOO7tjGbiwB21a2f-cnsyTPMz-T2OmgkMAqsCzmAp5oM2pv1qjNqzJzHZ0tY1deIpl4.; SUB=_2A2513wOYDeRhGeVO61MV9CvNyz2IHXVWrXJQrDV8PUNbmtAKLXjykW8MPNN5zD8LpzFJZYHrEGEX-XJ0Ag..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9Wh1N4gTo1oiJK9C1iqBoTNR5JpX5KMhUgL.Foe7eh2XSh-peh22dJLoIEXLxKBLBo.LBKqLxKML1KBL1-qLxKML1hnLBo2LxK-L1KBL1K5LxK-LB-qL1h-t; SUHB=02MvQCN0XIuZ8w; ALF=1522313032; SSOLoginState=1490777033; _s_tentry=login.sina.com.cn; UOR=,,login.sina.com.cn; Apache=2400217120009.1226.1490777037810; ULV=1490777037823:9:8:2:2400217120009.1226.1490777037810:1490705151329";

    public static void main(String[] args) {
        System.out.println("原始的 cookie 是：" + cookie);

        System.out.println("分割后的数据是：");
        System.out.println("");


        StringBuilder sb = new StringBuilder();
        String[] c = cookie.split("; ");

        for (String s :
                c) {
            System.out.println(s);
            String[] data = s.split("=");
            sb.append("\"" + data[0] + "\"");
            sb.append(":");
            sb.append("\"" + data[1] + "\"");
            sb.append(",\n");


        }
        System.out.println("");
        System.out.println("new Cookie:\n" + sb.toString());
    }

}
