package com.scg.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;


@Controller
@SpringBootApplication
public class GatewayApplication {




    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}