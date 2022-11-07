package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Alias("stock")
public class StockDTO {

    int product_id, one, two, three, four, five, six, total, quantity;
    private List<String> size;

}
