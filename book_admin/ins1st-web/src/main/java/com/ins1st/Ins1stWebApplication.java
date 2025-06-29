package com.ins1st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.ins1st")
public class Ins1stWebApplication {


    public static void main(String[] args) {
        SpringApplication.run(Ins1stWebApplication.class, args);
    }

}
