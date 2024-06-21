package com.zj.dubbo.controller;

import com.zj.dubbo.entity.Stock;
import com.zj.dubbo.serivce.StockService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class OrderController {

    /**
     * mock=force:return+null 表示消费方对该服务的方法调用都直接返回 null 值，不发起远程调用。用来屏蔽不重要服务不可用时对调用方的影响。
     * mock=fail:return+null 表示消费方对该服务的方法调用在失败后，再返回 null 值，不抛异常。用来容忍不重要服务不稳定时对调用方的影响。
     */

    /**
     * 消费端通过Dubbo远程调用服务端，其业务实现基本都在服务端。但有些时候想在消费端也执行部分逻辑，
     * 比如：做 ThreadLocal 缓存（这个用处最大），提前验证参数，调用失败后伪造容错数据等等，
     * 此时就需要在@Reference中带上 Stub，消费端生成服务的代理 Proxy 实例，
     * 会把 Proxy 通过构造函数传给 Stub，然后把 Stub 暴露给用户，Stub 可以决定要不要去调 Proxy
     */

    //stub 本地存根
    //@DubboReference(stub = "com.foo.DemoServiceStub") //指定stub对象
    //timeout 服务提供者端超时时间
    //cluster 集群容错
    //retries 重试次数
    //mock 服务降级：如果调用失败返回123
    @DubboReference(version = "v.1.0", timeout = 3000,retries = 1,cluster = "failfast", mock = "fail: return 123", stub = "true")
    private StockService stockService;


    @GetMapping("/create_order")
    public Map createOrder(Long skuId , Long salesQuantity){

        Map result = new LinkedHashMap();
        //查询商品库存，像调用本地方法一样完成业务逻辑。
        Stock stock = stockService.getStock(skuId);
        System.out.println(stock);
        if(salesQuantity <= stock.getQuantity()){
            //创建订单相关代码，此处省略
            //CODE=SUCCESS代表订单创建成功
            result.put("code" , "SUCCESS");
            result.put("skuId", skuId);
            result.put("message", "订单创建成功");
        }else{
            //code=NOT_ENOUGN_STOCK代表库存不足
            result.put("code", "NOT_ENOUGH_STOCK");
            result.put("skuId", skuId);
            result.put("message", "商品库存数量不足");
        }
        return result;

    }

}
