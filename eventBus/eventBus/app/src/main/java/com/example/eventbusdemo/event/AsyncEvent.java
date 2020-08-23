package com.example.eventbusdemo.event;

/**
 * @ClassName AsyncEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-10 22:13
 * @Version 1.0
 */
public class AsyncEvent {

    public String threadInfo;

    public AsyncEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
