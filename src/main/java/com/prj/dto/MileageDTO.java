package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("mileage")
public class MileageDTO {

    private int mileage_id, amount;
    private String id, type;
    private Date register;

}
