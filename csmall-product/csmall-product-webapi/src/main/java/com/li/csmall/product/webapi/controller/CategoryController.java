package com.li.csmall.product.webapi.controller;

import com.li.csmall.common.web.JsonResult;
import com.li.csmall.pojo.dto.CategoryAddNewDTO;
import com.li.csmall.pojo.vo.CategoryDetailsVO;
import com.li.csmall.pojo.vo.CategorySimpleListItemVO;
import com.li.csmall.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories",produces = "application/json; charset=utf-8")
public class CategoryController {
   @Autowired
   private ICategoryService categoryService;

    public CategoryController() {
        System.out.println("CategoryController.CategoryController");
    }

    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Validated CategoryAddNewDTO categoryAddNewDTO){
        categoryService.addNew(categoryAddNewDTO);
        return JsonResult.ok();
    }

    @GetMapping("/list-by-parent")
    public JsonResult<List<CategorySimpleListItemVO>> listByParentId(Long parentId){
        List<CategorySimpleListItemVO> categorySimpleListItemVOS = categoryService.listByParentId(parentId);
        return JsonResult.ok(categorySimpleListItemVOS);
    }

    @GetMapping("/{id}/get-details")
    public JsonResult<CategoryDetailsVO> getDetailsById(@PathVariable Long id){
        CategoryDetailsVO categoryDetailsVO = categoryService.getDetailsById(id);
        return JsonResult.ok(categoryDetailsVO);
    }
}
