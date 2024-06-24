package com.zj.dubbo.service.impl;

import com.zj.dubbo.service.MockSayHelloService;

// 相当于重新写了个预备服务Bean
public class MockSayHelloServiceImpl implements MockSayHelloService {
    @Override
    public String sayHello() {
        return "Sorry, 服务端发生异常，被降级啦！";
    }
}
