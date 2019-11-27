package com.cloud.licensingservice.event;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义通道
 */
public interface CustomChannels {
    // input定义了通道的名称
    @Input("inboundOrgChanges")
    // 通过@Input注解公开的每个通道必须返回一个SubscribableChannel类
    SubscribableChannel orgs();
}
