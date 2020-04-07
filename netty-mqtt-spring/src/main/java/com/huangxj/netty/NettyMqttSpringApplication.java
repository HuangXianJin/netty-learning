package com.huangxj.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangxj
 */
@SpringBootApplication(scanBasePackages = {"com.huangxj.netty"})
public class NettyMqttSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyMqttSpringApplication.class, args);
    }

}
