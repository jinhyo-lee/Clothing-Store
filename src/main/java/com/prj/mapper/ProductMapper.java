package com.prj.mapper;

import com.prj.dto.CategoryDTO;
import com.prj.dto.ProductDTO;
import com.prj.dto.StockDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    int getCategoryId(CategoryDTO category);

    int upload(ProductDTO product);

    void setStock(StockDTO stock);

    List<ProductDTO> getProductList(Map<String, Object> map);

    int getProductCount(int category_id);
    CategoryDTO getCategory(int category_id);

    ProductDTO getProduct(int product_id);

    StockDTO getStock(int product_id);

    CategoryDTO getCategoryByProduct(int product_id);

    List<ProductDTO> getSearchList(Map<String, Object> map);

    int getSearchCount(String product_name);

}
