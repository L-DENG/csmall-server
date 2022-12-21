package com.li.csmall.product.service;

import com.li.csmall.pojo.dto.CategoryAddNewDTO;
import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICategoryService {
    /**
     * add a new category
     *
     * @param categoryAddNewDTO
     */
    void addNew(CategoryAddNewDTO categoryAddNewDTO);

    /**
     *
     * @param id
     * @return list of CategorySimpleListItemVO where parent id equal to id;
     */
    List<CategorySimpleListItemVO> listByParentId(Long id);

    /**
     *
     * @param id
     * @return
     */
    CategoryDetailsVO getDetailsById(Long id);

    /**
     * preload the category data into redis
     */
    void preloadCache();
}
