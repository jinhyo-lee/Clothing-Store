package com.prj.mapper;

import com.prj.dto.DetailDTO;
import com.prj.dto.MemberDTO;
import com.prj.dto.MileageDTO;
import com.prj.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    List<OrderDTO> getOrderList(Map<String, Object> map);

    List<MileageDTO> getMileageList(Map<String, Object> map);

    OrderDTO getOrder(int order_id);

    List<DetailDTO> getDetailList(int order_id);

    int getOrderCount(String id);

    int getMileageCount(String id);

    void updateMember(MemberDTO member);

    String getPassword(String id);
    void changePassword(MemberDTO member);

    void quit(String id);

}
