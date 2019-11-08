package org.sfedu.sockets;

import org.sfedu.sockets.config.SocketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(SocketConfiguration.class)
@ComponentScan({"org.sfedu.sockets.config","org.sfedu.sockets.controller"})
public class SocketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketsApplication.class, args);
    }

}
