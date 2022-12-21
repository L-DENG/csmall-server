package com.li.csmall.admin.webapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CsmallAdminWebapiApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
    }

    @Test
    void connectionTest(){
        Assertions.assertDoesNotThrow(()->{
            dataSource.getConnection();
        });

    }

}
