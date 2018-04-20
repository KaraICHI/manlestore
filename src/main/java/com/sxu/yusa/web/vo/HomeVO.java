package com.sxu.yusa.web.vo;

import com.sxu.yusa.service.dto.CategoryDTO;
import com.sxu.yusa.service.dto.ProductDTO;

import java.util.List;

public class HomeVO {
    private List<CategoryDTO> categoryInfo;
    private List<ProductDTO> seckillInfo;
    private List<ProductDTO> recommendInfo;
    private List<ProductDTO> hotInfo;

    public List<CategoryDTO> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(List<CategoryDTO> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public List<ProductDTO> getSeckillInfo() {
        return seckillInfo;
    }

    public void setSeckillInfo(List<ProductDTO> seckillInfo) {
        this.seckillInfo = seckillInfo;
    }

    public List<ProductDTO> getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(List<ProductDTO> recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    public List<ProductDTO> getHotInfo() {
        return hotInfo;
    }

    public void setHotInfo(List<ProductDTO> hotInfo) {
        this.hotInfo = hotInfo;
    }
}
