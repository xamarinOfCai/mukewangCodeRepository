package com.example.eventbusdemo.event;

/**
 * @ClassName MainOrderedEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-10 21:34
 * @Version 1.0
 */
public class MainOrderedEvent {

    public String threadInfo;

    public MainOrderedEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
