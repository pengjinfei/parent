package com.pjf.core.bean.product;

import java.io.Serializable;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public class BrandQuery implements Serializable {
    //品牌ID  bigint
    private Long id;
    //品牌名称
    private String name;
    //描述
    private String description;
    //图片URL
    private String imgUrl;
    //排序  越大越靠前
    private Integer sort;
    //是否可用   0 不可用 1 可用
    private Integer isDisplay;//is_display
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getIsDisplay() {
        return isDisplay;
    }
    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }
    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", description=" + description + ", imgUrl=" + imgUrl + ", sort="
                + sort + ", isDisplay=" + isDisplay + "]";
    }
    public static final Integer PAGE_SIZE = 10;
    //当前页
    private Integer pageNo = 1;
    //每页数
    private Integer pageSize = PAGE_SIZE;
    //开始行
    private Integer startRow;


    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        //计算开始行
        this.startRow = (pageNo - 1)*pageSize;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        //计算开始行
        this.startRow = (pageNo - 1)*pageSize;
    }
    public Integer getStartRow() {
        return startRow;
    }
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

}
