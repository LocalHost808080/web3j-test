package com.xkb.web3j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.spring.autoconfigure.Web3jAutoConfiguration;

@SpringBootApplication(exclude = Web3jAutoConfiguration.class)
public class Web3jTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(Web3jTestApplication.class, args);
    }
}
