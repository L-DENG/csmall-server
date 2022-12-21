package com.li.csmall.product.webapi.service;

import com.li.csmall.common.ex.ServiceException;
import com.li.csmall.common.web.State;
import com.li.csmall.pojo.dto.CategoryAddNewDTO;
import com.li.csmall.pojo.entity.Category;
import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import com.li.csmall.pojo.vo.CategorySimplyVO;
import com.li.csmall.product.service.ICategoryService;
import com.li.csmall.product.webapi.mapper.CategoryMapper;
import com.li.csmall.product.webapi.repository.ICategoryRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryRedisRepository categoryRedisRepository;

    @Override
    public void addNew(CategoryAddNewDTO categoryAddNewDTO) {
        log.trace("start to addNew ...");
        String name = categoryAddNewDTO.getName();
        CategorySimplyVO categorySimplyVO = categoryMapper.getByName(name);
        if (categorySimplyVO != null) {
            log.trace("this category has aready exit");
            throw new ServiceException(State.ERR_CATEGORY_NAME_DUPLICATE,"Add new category fail, caused by duplicate category name");
        }
        int depth = 1;
        CategorySimplyVO parentSimplyVO = null;

        Category category = new Category();
        Long parentId = categoryAddNewDTO.getParentId();
        if (parentId != 0) {
            parentSimplyVO = categoryMapper.getById(parentId);
            if (parentSimplyVO == null) {
                log.trace("parent does not exit!!");
                throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,"Add new category fail, caused by parent category does not exist");
            }
            depth = parentSimplyVO.getDepth() + 1;

        }
        category.setDepth(depth);
        category.setIsParent(0);
        category.setEnable(1);
        category.setGmtCreate(LocalDateTime.now());
        category.setGmtModified(LocalDateTime.now());
        BeanUtils.copyProperties(categoryAddNewDTO, category);
        int rows = categoryMapper.insert(category);
        if (rows != 1) {
            log.trace("insert the category fail");
            throw new ServiceException(State.ERR_INSERT,"Add category fail,caused by the server is busy("+State.ERR_INSERT.getValue()+"),please try it later");
        }
        log.trace("successfully insert the category");

        log.trace("try to update the is_parent column of the parent category");
        if (parentId!= 0 && parentSimplyVO.getIsParent() == 0) {
            int row = categoryMapper.updateIsParentById(parentId, 1);
            if (row != 1) {
                log.trace("update is_parent fail");
                throw new ServiceException(State.ERR_UPDATE,"\"Add category fail,caused by the server is busy("+State.ERR_UPDATE.getValue()+"),please try it later\"");
            }
            log.trace("update is_parent successfully");
        }
    }

    @Override
    public List<CategorySimpleListItemVO> listByParentId(Long parentId) {
        return categoryMapper.listByParentId(parentId);
    }

    @Override
    public CategoryDetailsVO getDetailsById(Long id) {
        boolean exist = categoryRedisRepository.exist(id);
        if(exist){
            CategoryDetailsVO cacheResult = categoryRedisRepository.getDetailsById(id);
            if (cacheResult==null){
                log.warn("==============data is illegal in  redis=========");
                throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,"Fail to get category detail due to the data in the cache is illegal");
            }else{
                log.trace("==============data from redis=========");
                return cacheResult;
            }
        }

        CategoryDetailsVO dbResult = categoryMapper.getDetailsById(id);
        if (dbResult==null){
            log.warn("data with (id={}) does not in the database ",id);
            categoryRedisRepository.saveEmpty(id);
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,"Fail to get category detail due to the data does not exist");
        }
        categoryRedisRepository.save(dbResult);
        log.trace("==============data from mapper=========");
        return dbResult;
    }

    @Override
    public void preloadCache() {
        categoryRedisRepository.deleteList();
        categoryRedisRepository.deleteAllItems();
        List<CategoryDetailsVO> list = categoryMapper.list();
        for (CategoryDetailsVO category : list) {
            log.debug("\t the results : {}",category);
            categoryRedisRepository.save(category);
        }
        categoryRedisRepository.save(list);
    }
}
