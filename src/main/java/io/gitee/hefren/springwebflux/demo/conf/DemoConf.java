package io.gitee.hefren.springwebflux.demo.conf;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/9/29
 * @Author lifei
 */
@Configuration
public class DemoConf {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/demoController/sayHello", RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        (ServerRequest request)->ServerResponse.ok().body(Mono.just("RouterFunction say Hello: " + request.queryParam("name").get()), String.class))
                .build();
    }

    @Bean
    public ApplicationRunner runner(ApplicationContext applicationContext) {
        return args->{// 
            SimpleUrlHandlerMapping simpleUrlHandlerMapping = applicationContext.getBean("resourceHandlerMapping", SimpleUrlHandlerMapping.class);
            Map<String, WebHandler> urlMap = new HashMap<>();
            urlMap.put("/demoController/sayHello", (ServerWebExchange exchange) -> {
                String data = "SimpleUrlHanlderMapping say Hello: " + exchange.getRequest().getQueryParams().get("name");
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(data.getBytes())));
            });
            urlMap.put("/demoController/saySimpleHello", (ServerWebExchange exchange) -> {
                String data = "SimpleUrlHanlderMapping say Hello: " + exchange.getRequest().getQueryParams().get("name");
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(data.getBytes())));
            });
            simpleUrlHandlerMapping.setUrlMap(urlMap);
            simpleUrlHandlerMapping.initApplicationContext();
        };
    }


//    @Bean
//    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
//       SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
//       Map<String, WebHandler> urlMap = new HashMap<>();
//       urlMap.put("/demoController/sayHello", (ServerWebExchange exchange)->{
//           String data = "SimpleUrlHanlderMapping say Hello: " + exchange.getRequest().getQueryParams().get("name");
//           return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(data.getBytes())));
//       });
//        urlMap.put("/demoController/saySimpleHello", (ServerWebExchange exchange)->{
//            String data = "SimpleUrlHanlderMapping say Hello: " + exchange.getRequest().getQueryParams().get("name");
//            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(data.getBytes())));
//        });
//       simpleUrlHandlerMapping.setUrlMap(urlMap);
//       simpleUrlHandlerMapping.setOrder(-2);
//       return simpleUrlHandlerMapping;
//    }
}
