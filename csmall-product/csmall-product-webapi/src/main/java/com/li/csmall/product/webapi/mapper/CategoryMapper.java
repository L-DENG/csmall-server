package com.li.csmall.product.webapi.mapper;


import com.li.csmall.pojo.entity.Category;
import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import com.li.csmall.pojo.vo.CategorySimplyVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CategoryMapper
 */
@Repository
public interface CategoryMapper {
    /**
     * insert data into category
     * @param category
     * @return the number of rows affected
     */
    int insert(Category category);

    /**
     *
     * @param id type: Long
     * @param isParent 1=yes 0=no
     * @return the number of rows affected
     */
    int updateIsParentById(@Param("id") Long id, @Param("isParent") Integer isParent);

    /**
     * select simply category info by category name
     * @param name
     * @return
     */
    CategorySimplyVO getByName(String name);

    /**
     *
     * @param id
     * @return CategorySimplyVO type
     */
    CategorySimplyVO getById(long id);

    /**
     *
     * @param id
     * @return list of category where parent id equal to id
     */
    List<CategorySimpleListItemVO> listByParentId(Long parentId);


    /**
     *
     * @param id
     * @return
     */
    CategoryDetailsVO getDetailsById(Long id);

    /**
     *
     * @return
     */
    List<CategoryDetailsVO> list();



}
