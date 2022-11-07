package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("detail")
public class DetailDTO {

    private int detail_id, order_id, product_id, quantity, price;
    private String size;

    private String product_name, image;

}
