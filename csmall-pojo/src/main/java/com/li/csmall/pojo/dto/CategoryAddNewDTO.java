package com.li.csmall.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryAddNewDTO implements Serializable {
    @NotNull(message = "Fail to add new category, must fill in category name")
    private String name;
    private long parentId;
    private String keywords;
    private Integer sort;
    private String icon;
    private Integer isDisplay;
}
