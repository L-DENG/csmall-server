package com.li.csmall.product.webapi.repository;

import com.li.csmall.pojo.vo.CategoryDetailsVO;

import java.util.List;


public interface ICategoryRedisRepository {

    String KEY_CATEGORY_ITEM_PREFIX = "categories:item:";
    String KEY_CATEGORY_LIST = "categories:list:";

    /**
     *
     * @param categoryDetailsVO
     */
    void save(CategoryDetailsVO categoryDetailsVO);

    /**
     * store the list of category into the redis
     * @param categories
     */
    void save(List<CategoryDetailsVO> categories);

    void saveEmpty(Long id);

    Boolean deleteList();

    void deleteAllItems();

    Boolean exist(Long id);

    /**
     *
     * @param id
     * @return
     */
    CategoryDetailsVO getDetailsById(Long id);
}
