package com.zj.dubbo.serivce.impl;

import com.zj.dubbo.serivce.RestDemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.rest.support.ContentType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * 先讲配置文件协议为rest
 * dubbo.protocol.name=rest
 *
 * 服务端实现：使用@Path指定Rest风格的访问路径（注意：所有暴露的服务都必须加@Path）
 * javax.ws.rs
 */
@DubboService(version = "rest")
@Path("demo")
public class RestDemoServiceImpl implements RestDemoService {

    @GET
    @Path("say")
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public String sayHello(@QueryParam("name") String name) {
        System.out.println("执行了rest服务" + name);

        URL url = RpcContext.getContext().getUrl();
        return String.format("%s: %s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问
    }
}
