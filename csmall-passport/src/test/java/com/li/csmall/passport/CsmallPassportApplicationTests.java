package com.li.csmall.passport;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsmallPassportApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        System.out.println("CsmallPassportApplicationTests.contextLoads");
    }

    @Test
    void connectionTest(){
        assertDoesNotThrow(()->{
            dataSource.getConnection();
        });
    }

}
