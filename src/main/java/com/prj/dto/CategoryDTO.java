package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("category")
public class CategoryDTO {

    private int category_id, parent_id;
    private String category_name;

}
