package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class ServletTest {

    public static void main(String[] args){

        ServletSource servletSource = new ServletSource( "www.travis.wpay.com","微信支付", new ServletListener() {
            @Override
            public void createWebApplicationContext(ServletSource servletSource1) {

                System.out.print("判断该地址:"+servletSource1.getUrl()+"是否可用\n");
            }
        });

    }
}
