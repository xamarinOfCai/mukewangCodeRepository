package com.example.eventbusdemo.event;

/**
 * @ClassName SuccessEvent
 * @Description
 * @Author cjj
 * @Date 2020-08-09 20:40
 * @Version 1.0
 */
public class SuccessEvent {

    public String threadInfo;

    public SuccessEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
