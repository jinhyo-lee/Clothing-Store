package com.prj.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("member")
public class MemberDTO {

    public String id, password, name, address, contact, email, birth, role;
    private Date register;
    private int mileage, enable;

}
