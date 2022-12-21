package com.li.csmall.product.webapi.repository;
import com.li.csmall.pojo.vo.CategoryDetailsVO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRedisRepositoryTest {

    @Autowired
    ICategoryRedisRepository repository;


    @Test
    void testSave(){
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(10L);
        category.setName("category-redis-001");
        repository.save(category);
    }

    @Test
    void testGetDetailsByIdSuccessfully(){
        testSave();
        assertDoesNotThrow(()->{
           CategoryDetailsVO detailsById = repository.getDetailsById(10L);
           assertNotNull(detailsById);
        });

    }
    @Test
    void testGetDetailsByIdFail(){
        testSave();
        assertDoesNotThrow(()->{
            CategoryDetailsVO detailsById = repository.getDetailsById(-1L);
            assertNull(detailsById);
        });

    }
}
