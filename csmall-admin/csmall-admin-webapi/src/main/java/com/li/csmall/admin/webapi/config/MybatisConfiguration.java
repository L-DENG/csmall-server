package com.li.csmall.admin.webapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.li.csmall.admin.webapi.mapper")
public class MybatisConfiguration {
}
