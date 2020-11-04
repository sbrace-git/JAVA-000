package io.github.kimmking.gateway;


import io.github.kimmking.gateway.inbound.HttpInboundServer;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.HttpEndpointRouterImpl;
import io.github.kimmking.gateway.router.RouterEnum;

import java.util.Arrays;
import java.util.List;

public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        String proxyServer1 = System.getProperty("proxyServer1", "http://localhost:8801");
        String proxyServer2 = System.getProperty("proxyServer2", "http://localhost:8802");
        String proxyServer3 = System.getProperty("proxyServer3", "http://localhost:8803");
        List<String> proxyServerList = Arrays.asList(proxyServer1, proxyServer2, proxyServer3);

        String proxyPort = System.getProperty("proxyPort", "8888");

        //设置路由
        HttpEndpointRouter router = null;
        //随机
        //router = new HttpEndpointRouterImpl(proxyServerList);
        //轮询
        //router = new HttpEndpointRouterImpl(proxyServerList, RouterEnum.ROUND_RIBBON);
        //权重
        router = new HttpEndpointRouterImpl(proxyServerList, RouterEnum.WEIGHT,5,2,3);

        //  http://localhost:8888/api/hello  ==> gateway API
        //  http://localhost:8088/api/hello  ==> backend service

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(port, router);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + proxyServerList.toString());
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
