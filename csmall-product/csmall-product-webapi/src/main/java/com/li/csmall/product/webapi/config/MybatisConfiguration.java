package com.li.csmall.product.webapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.li.csmall.product.webapi.mapper")
public class MybatisConfiguration {

}
