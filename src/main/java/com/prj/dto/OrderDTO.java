package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("order")
public class OrderDTO {

    private int order_id, cost, discount;
    private String id, address, shipping, status, payment, bankName, accountName, accountNumber;
    private Date order_date;

    private int product_id;
    private String product_name, price;

}
