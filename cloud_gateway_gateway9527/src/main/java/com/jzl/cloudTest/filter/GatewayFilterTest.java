package com.jzl.cloudTest.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class GatewayFilterTest implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("time:"+new Date()+"\t 执行了自定义的全局过滤器: "+"GatewayFilterTest"+"hi~");

        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null){
            log.debug("请输入用户名重试");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        log.debug("uname");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
