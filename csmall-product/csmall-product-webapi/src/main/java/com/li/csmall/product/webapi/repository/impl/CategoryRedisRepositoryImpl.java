package com.li.csmall.product.webapi.repository.impl;

import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.product.webapi.repository.ICategoryRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class CategoryRedisRepositoryImpl implements ICategoryRedisRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void save(CategoryDetailsVO categoryDetailsVO) {
        String key = KEY_CATEGORY_ITEM_PREFIX + categoryDetailsVO.getId();
        redisTemplate.opsForValue().set(key,categoryDetailsVO);
    }

    @Override
    public void save(List<CategoryDetailsVO> categories) {
        for (CategoryDetailsVO category : categories) {
            String key = KEY_CATEGORY_LIST;
            redisTemplate.opsForList().rightPush(key,category);
        }

    }

    @Override
    public void saveEmpty(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        redisTemplate.opsForValue().set(key,null,30, TimeUnit.SECONDS);
    }

    @Override
    public Boolean exist(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        return redisTemplate.hasKey(key);
    }

    @Override
    public Boolean deleteList() {
        return redisTemplate.delete(KEY_CATEGORY_LIST);
    }

    @Override
    public void deleteAllItems() {
        Set<String> keys = redisTemplate.keys(KEY_CATEGORY_ITEM_PREFIX + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public CategoryDetailsVO getDetailsById(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        Serializable category = redisTemplate.opsForValue().get(key);
        if (category == null){
            return null;
        }else{
            CategoryDetailsVO categoryDetailsVO = (CategoryDetailsVO) category;
            return categoryDetailsVO;
        }
    }
}
