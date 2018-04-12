package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ThemeMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    @Mapping(source = "theme.id", target = "themeId")
    @Mapping(source = "theme.themeName", target = "themeThemeName")
    ProductDTO toDto(Product product);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "themeId", target = "theme")
    @Mapping(target = "clientUsers", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
