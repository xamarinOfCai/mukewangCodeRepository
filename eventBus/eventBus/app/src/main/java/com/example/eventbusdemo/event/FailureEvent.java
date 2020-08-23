package com.example.eventbusdemo.event;

/**
 * @ClassName FailureEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-09 20:43
 * @Version 1.0
 */
public class FailureEvent {

    public String threadInfo;

    public FailureEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
