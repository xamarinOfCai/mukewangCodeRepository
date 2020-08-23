package com.example.eventbusdemo.event;

/**
 * @ClassName PostingEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-09 22:36
 * @Version 1.0
 */
public class PostingEvent {
    private String threadInfo;
    public PostingEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
