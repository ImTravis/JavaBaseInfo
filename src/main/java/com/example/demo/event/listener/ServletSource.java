package com.example.demo.event.listener;

/**
 * @author 作者 xcc:
 * @version 创建时间：
 * 类说明
 */
public class ServletSource {
    private String url;
    private String application;

    private ServletListener servletListener;


    public ServletSource(String url,String application,ServletListener servletListener) {
        this.url = url;

        System.out.print("事件源创建准备中\n");
        if(servletListener != null){
            servletListener.createWebApplicationContext(this);
        }

        this.application = application;
        System.out.print("事件源创建完毕："+application+"地址："+url+"\n");

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
