package com.prj.mapper;

import com.prj.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int login(MemberDTO member);

    int idCheck(String id);

    void join(MemberDTO member);

    MemberDTO getMember(String id);

}
