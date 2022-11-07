package com.prj.service;

import com.prj.dto.DetailDTO;
import com.prj.dto.MemberDTO;
import com.prj.dto.MileageDTO;
import com.prj.dto.OrderDTO;
import com.prj.dto.ProductDTO;
import com.prj.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<OrderDTO> getOrderList(Map<String, Object> map) {
        List<OrderDTO> orderList = mapper.getOrderList(map);

        return orderList;
    }

    public List<MileageDTO> getMileageList(Map<String, Object> map) {
        return mapper.getMileageList(map);
    }

    public OrderDTO getOrder(int order_id) {
        return mapper.getOrder(order_id);
    }

    public List<DetailDTO> getDetailList(int order_id) {
        return mapper.getDetailList(order_id);
    }

    public int getOrderCount(String id) {
        return mapper.getOrderCount(id);
    }

    public int getMileageCount(String id) {
        return mapper.getMileageCount(id);
    }

    public void updateMember(MemberDTO member) {
        mapper.updateMember(member);
    }

    public int checkPassword(String id, String password) {
        String currentPassword = mapper.getPassword(id);

        if (passwordEncoder.matches(password, currentPassword))
            return 1;
        else
            return 0;
    }

    public void changePassword(MemberDTO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        mapper.changePassword(member);
    }

    public void quit(String id) {
        mapper.quit(id);
    }

}
