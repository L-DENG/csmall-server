package com.li.csmall.product.webapi;


import com.li.csmall.pojo.vo.CategoryDetailsVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    @Test
    void testSetValue(){
        redisTemplate.opsForValue()
                .set("name","li deng-----get offer");
    }

    @Test
    void testSetValueTTL(){
        redisTemplate.opsForValue()
                .set("name-ttl","li deng-----get offer",60, TimeUnit.SECONDS);
    }

    @Test
    void  testGetValue(){
        Serializable name = redisTemplate.opsForValue().get("name");
        System.out.println("get value  ----> "+name);
    }

    @Test
    void testSetObjectValue(){
        CategoryDetailsVO categoryDetailsVO = new CategoryDetailsVO();
        categoryDetailsVO.setId(5L);
        categoryDetailsVO.setName("redis-category-test");
        categoryDetailsVO.setIsDisplay(1);
        categoryDetailsVO.setIsParent(1);
        redisTemplate.opsForValue()
                .set("category",categoryDetailsVO);
    }

    @Test
    void  testGetObjectValue(){
        Serializable category = redisTemplate.opsForValue().get("category");
        System.out.println("serialization get value --->"+category);
        if (category != null) {
            CategoryDetailsVO categoryDetailsVO = (CategoryDetailsVO) category;
            System.out.println("get value  ----> "+categoryDetailsVO);
        }
    }

    @Test
    void testDeleteKey(){
        boolean res = redisTemplate.delete("name");
        System.out.println("result --> "+ res);
    }


    @Test
    void testSetObjectList(){
        List<CategoryDetailsVO> list = new ArrayList<>();
        for (Long i = 1L; i <=5L ; i++) {
            CategoryDetailsVO categoryDetailsVO = new CategoryDetailsVO();
            categoryDetailsVO.setId(i);
            categoryDetailsVO.setName("redis-category-list-test--"+i);
            list.add(categoryDetailsVO);
        }

        for (CategoryDetailsVO category : list) {
            redisTemplate.opsForList().rightPush("categoryList",category);
        }
    }

    @Test
    void testListSize(){
        String key = "categoryList";
        Long size = redisTemplate.opsForList().size(key);
        System.out.println("categoryList size ====>>> "+size);
    }

    @Test
    void testRange(){
        String key = "categoryList";

        List<Serializable> range = redisTemplate.opsForList().range(key,0,-1);

        for (Serializable serializable : range) {
            System.out.println(serializable);

        }
    }

    @Test
    void testKeys(){
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
