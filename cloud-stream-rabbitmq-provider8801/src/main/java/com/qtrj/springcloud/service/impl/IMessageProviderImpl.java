package com.qtrj.springcloud.service.impl;

import com.qtrj.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@EnableBinding(Source.class) // 消息输出管道 output
public class IMessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String s = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(s).build());
        log.info("***** serial: " + s);
        return null;
    }
}
