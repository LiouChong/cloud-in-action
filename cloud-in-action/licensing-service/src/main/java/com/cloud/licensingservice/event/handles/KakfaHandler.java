package com.cloud.licensingservice.event.handles;

import com.cloud.licensingservice.LicensingServiceApplication;
import com.cloud.licensingservice.event.models.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/25 14:59
 */
@EnableBinding(Sink.class)
public class KakfaHandler {

    private static final Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);

    /**
     * 这个注解告诉spring cloud stream，每次从input通道接收消息，就会执行
     * loggerSink()方法。spring cloud stream会自动把从通道中传出的消息
     * 反序列化为一个OrganizationChangeModel的Java POJO
     * @param orgChange
     */
    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
        System.out.println("------------------------------接收到消息------------------------------");
        System.out.println(orgChange.getAction() + " --  "  + orgChange.getType());
    }

}
