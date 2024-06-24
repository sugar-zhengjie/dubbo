package com.zj.dubbo.serivce.impl;


import com.zj.dubbo.entity.Stock;
import com.zj.dubbo.serivce.StockService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.Map;

/**
 * Dubbo提供了六种集群容错方案：
 *
 * Failover Cluster：失败自动切换
 * 当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 retries=“2” 来设置重试次数(不含第一次)。
 * Failfast Cluster：快速失败
 * 只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录
 * Failsafe Cluster：失败安全
 * 出现异常时，不抛异常，直接忽略。通常用于写入审计日志等操作
 * Failback Cluster：失败自动恢复
 * 后台记录失败请求，定时重发。通常用于消息通知操作
 * Forking Cluster：并行调用多个服务器
 * 只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks=“2” 来设置最大并行数
 * Broadcast Cluster：广播调用所有提供者
 * 逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息
 *
 * RoundRobinLoadBalance 加权轮询算法
 * RandomLoadBalance 权重随机算法：根据权重值进行随机负载
 * LeastActiveLoadBalance 最少活跃调用数算法：活跃调用数越小，表明该服务提供者效率越高，单位时间内可处理更多的请求这个是比较科学的负载均衡算法。
 * ConsistentHashLoadBalance hash一致性算法：相同参数的请求总是发到同一提供者 当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者， 不会引起剧烈变动
 *
 * random、roundrobin、leastactive、consistenthash
 */
//timeout 服务提供者端超时时间
//cluster 集群容错
@DubboService(version = "v.1.0", timeout = 4000, cluster = "failfast", loadbalance = "roundrobin")
public class StockServiceImpl implements StockService {

    public Stock getStock(Long skuId){
        Map result = new HashMap();
        Stock stock = null;
        if(skuId == 1101l){
            //模拟有库存商品
            stock = new Stock(1101l, "Apple iPhone 15 128GB 紫色", 32, "台");
            stock.setDescription("Apple 15 紫色版对应商品描述");
        }else if(skuId == 1102l){
            //模拟无库存商品
            stock = new Stock(1102l, "Apple iPhone 15 256GB 白色", 0, "台");
            stock.setDescription("Apple 15 白色版对应商品描述");
        }else{
            //演示案例，暂不考虑无对应skuId的情况
        }

        return stock;

    }

}
