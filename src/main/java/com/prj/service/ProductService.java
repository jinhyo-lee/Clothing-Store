package com.prj.service;

import com.prj.dto.CategoryDTO;
import com.prj.dto.ProductDTO;
import com.prj.dto.StockDTO;
import com.prj.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;

    public int getCategoryId(CategoryDTO category) {
        return mapper.getCategoryId(category);
    }

    public int upload(ProductDTO product) {
        mapper.upload(product);

        return product.getProduct_id();
    }

    public void setStock(StockDTO stock) {
        mapper.setStock(stock);
    }

    public List<ProductDTO> getProductList(Map<String, Object> map) {
        return mapper.getProductList(map);
    }

    public int getProductCount(int category_id) {
        return mapper.getProductCount(category_id);
    }

    public CategoryDTO getCategory(int category_id) {
        return mapper.getCategory(category_id);
    }

    public ProductDTO getProduct(int product_id) {
        return mapper.getProduct(product_id);
    }

    public StockDTO getStock(int product_id) {
        return mapper.getStock(product_id);
    }

    public CategoryDTO getCategoryByProduct(int product_id) {
        return mapper.getCategoryByProduct(product_id);
    }

    public List<ProductDTO> getSearchList(Map<String, Object> map) {
        return mapper.getSearchList(map);
    }

    public int getSearchCount(String product_name) {
        return mapper.getSearchCount(product_name);
    }

}
