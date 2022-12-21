package com.li.csmall.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySimplyVO implements Serializable {
    private long id;
    private String name;
    private Integer depth;
    private Integer isParent;
}
