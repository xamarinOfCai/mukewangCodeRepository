package com.example.eventbusdemo.event;

/**
 * @ClassName BackGroundEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-10 21:58
 * @Version 1.0
 */
public class BackGroundEvent {
    public String threadInfo;

    public BackGroundEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
