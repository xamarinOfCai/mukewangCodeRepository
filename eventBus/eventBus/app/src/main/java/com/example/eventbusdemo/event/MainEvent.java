package com.example.eventbusdemo.event;

/**
 * @ClassName MainEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-10 21:11
 * @Version 1.0
 */
public class MainEvent {

    private String mThreadInfo;

    public MainEvent(String threadInfo) {
        mThreadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return mThreadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        mThreadInfo = threadInfo;
    }
}
