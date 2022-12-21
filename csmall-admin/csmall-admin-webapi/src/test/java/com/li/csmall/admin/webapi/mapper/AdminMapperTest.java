package com.li.csmall.admin.webapi.mapper;

import com.li.csmall.pojo.dto.AdminPermissionSimpleDTO;
import com.li.csmall.pojo.entity.Admin;
import static org.junit.jupiter.api.Assertions.*;

import com.li.csmall.pojo.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Slf4j
public class AdminMapperTest {

    @Autowired
    AdminMapper mapper;

    @Test
    @Sql("classpath:truncate.sql")
    void insertTest(){
        Admin admin = new Admin();
        admin.setUsername("admin-test");
        assertDoesNotThrow(()->{
            int rows = mapper.insert(admin);
            assertEquals(1,rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_data.sql"})
    void testGetByUsername(){
       String username = "root";
        assertDoesNotThrow(()->{
            AdminPermissionSimpleDTO byUsername = mapper.getByUsername(username);
            List<Permission> value = byUsername.getValue();
            for (Permission permission : value) {
                System.out.println(permission);
            }
        });
    }

}
