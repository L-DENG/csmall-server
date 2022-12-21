package com.li.csmall.product.webapi;

import com.li.csmall.common.config.CsmallCommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CsmallCommonConfiguration.class)
public class CsmallProductWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsmallProductWebapiApplication.class, args);
    }

}
