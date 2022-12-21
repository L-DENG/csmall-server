package com.li.csmall.passport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.li.csmall.passport.mapper")
public class MybitsConfiguration {
}
