package com.li.csmall.product.webapi.mapper;

import com.li.csmall.pojo.entity.Category;
import static org.junit.jupiter.api.Assertions.*;

import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import com.li.csmall.pojo.vo.CategorySimplyVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    CategoryMapper mapper;

    @Test
    @Sql("classpath:truncate.sql")
    public void insertTest(){
        Category c = new Category();
        c.setName("手机");
        assertDoesNotThrow(()->{
            int rows = mapper.insert(c);
            assertEquals(1,rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void updateIsParentSuccessfulTest(){
        assertDoesNotThrow(()->{
            int rows = mapper.updateIsParentById(1L, 0);
            assertEquals(1,rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void updateIsParentFailTest(){
        assertDoesNotThrow(()->{
            int rows = mapper.updateIsParentById(9999L, 1);
            assertEquals(0,rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void getByNameTest(){
        String name = "category-001";
        assertDoesNotThrow(()->{
            CategorySimplyVO category = mapper.getByName(name);
            System.out.println(category);
            assertNotNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void getByNameNullTest(){
        String name = "category-999-null";
        assertDoesNotThrow(()->{
            CategorySimplyVO category = mapper.getByName(name);
            assertNull(category);
        });
    }


    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void getByIdTest(){
        assertDoesNotThrow(()->{
            CategorySimplyVO category = mapper.getById(1L);
            System.out.println(category);
            assertNotNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void getByIdNullTest(){
        assertDoesNotThrow(()->{
            CategorySimplyVO category = mapper.getById(999L);
            assertNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void listByParentIdSuccessfullyTest(){
        List<CategorySimpleListItemVO> categorySimpleListItemVOS = mapper.listByParentId(5L);
        for (CategorySimpleListItemVO categorySimpleListItemVO : categorySimpleListItemVOS) {
            System.out.println(categorySimpleListItemVO);
        }
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void getDetailsByIdSuccessfullyTest(){
        assertDoesNotThrow(()->{
                CategoryDetailsVO detailsById = mapper.getDetailsById(1L);
                assertNotNull(detailsById);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void getDetailsByIdFailTest(){
        assertDoesNotThrow(()->{
            CategoryDetailsVO detailsById = mapper.getDetailsById(-1L);
            assertNull(detailsById);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void listTest(){
        assertDoesNotThrow(()->{
            List<CategoryDetailsVO> list = mapper.list();
            for (CategoryDetailsVO categoryDetailsVO : list) {
                System.out.println(categoryDetailsVO);
            }
        });
    }

}
