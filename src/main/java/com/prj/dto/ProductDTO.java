package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("product")
public class ProductDTO {

    private int product_id, category_id, price, sales;
    private String product_name, detail, material, stock, image;
    private Date register;

}
