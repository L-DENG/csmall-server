package com.li.csmall.product.webapi.service;


import com.li.csmall.common.ex.ServiceException;
import com.li.csmall.pojo.dto.CategoryAddNewDTO;
import static org.junit.jupiter.api.Assertions.*;

import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired(required = false)
    CategoryServiceImpl service;

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    void testAddNewSuccessfully(){
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("phone");
        category.setParentId(0L);
        category.setKeywords("not set the keywords");
        category.setSort(111);
        category.setIcon("noe set the icon");
        category.setIsDisplay(1);
        assertDoesNotThrow(()->{
            service.addNew(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    void testAddNewAddParentSuccessfully(){
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("smart-02 phone");
        category.setParentId(1L);
        category.setKeywords("not set the keywords");
        category.setSort(188);
        category.setIcon("have not upload the icon");
        category.setIsDisplay(1);
        assertDoesNotThrow(()->{
            service.addNew(category);
        });
    }

    @Test
    void testAddNewParentNotFound(){
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("error phone");
        category.setParentId(-1L);
        category.setKeywords("not set the keywords");
        category.setSort(188);
        category.setIcon("noe set the icon");
        category.setIsDisplay(1);
        assertThrows(ServiceException.class,()->{
            service.addNew(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    void testListByParentIdSuccessfully(){
        List<CategorySimpleListItemVO> categorySimpleListItemVOS = service.listByParentId(-1L);
        System.out.println(categorySimpleListItemVOS);
    }


    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    void testGetDetaildByIdSuccessfully(){
        assertDoesNotThrow(()->{
            Object category= service.getDetailsById(1L);
            assertNotNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    void testGetDetaildByIdFail(){
        assertThrows(ServiceException.class,()->{
            Object category= service.getDetailsById(999L);
        });
    }
}
