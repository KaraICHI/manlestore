package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.CategoryService;
import com.sxu.yusa.service.OrderItemService;
import com.sxu.yusa.service.ProductService;
import com.sxu.yusa.domain.Product;
import com.sxu.yusa.repository.ProductRepository;
import com.sxu.yusa.service.dto.ProductDTO;
import com.sxu.yusa.service.mapper.ProductMapper;
import com.sxu.yusa.web.vo.HomeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        return productMapper.toDto(product);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }

    @Override
    public List<ProductDTO> findHotProducts() {
        return productMapper.toDto(productRepository.findAllByOrderBySellDesc());
    }

    @Override
    public List<ProductDTO> findRecommendProducts() {
        return productMapper.toDto(productRepository.findAllByOrderByProduceDateDesc());
    }
    public List<ProductDTO> findSeckillProducts(){
        return productMapper.toDto(productRepository.findAllByOrderByOriginPriceDesc());
    }

    @Override
    public HomeVO getHomeInfo() {
        HomeVO homeVO = new HomeVO();
        homeVO.setCategoryInfo(categoryService.findAll());
        homeVO.setHotInfo(this.findHotProducts());
        homeVO.setRecommendInfo(this.findRecommendProducts());
        homeVO.setSeckillInfo(this.findSeckillProducts());
        return homeVO;
    }
}
