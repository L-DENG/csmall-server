package com.li.csmall.passport.mapper;

import com.li.csmall.common.ex.ServiceException;
import com.li.csmall.pojo.vo.AdminLoginVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminMapperTest {

    @Autowired
    AdminMapper adminMapper;

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_data.sql"})
    void testGetByUsername(){
        String username = "root";
        assertDoesNotThrow(()->{
            AdminLoginVO loginInfo = adminMapper.getLoginInfoByUsername(username);
            assertNotNull(loginInfo);
            System.out.println(loginInfo);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    void testGetByUsernameFail(){
        String username = "root-fail";
        assertDoesNotThrow(()->{
            AdminLoginVO loginInfo = adminMapper.getLoginInfoByUsername(username);
            assertNull(loginInfo);
        });
    }
}
