package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("cart")
public class CartDTO {

    private int cart_id, product_id, quantity, price;
    private String id, size, product_name, image;

}
