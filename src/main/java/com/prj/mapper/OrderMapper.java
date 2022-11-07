package com.prj.mapper;

import com.prj.dto.CartDTO;
import com.prj.dto.DetailDTO;
import com.prj.dto.MileageDTO;
import com.prj.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<CartDTO> getCart(String id);

    Integer getTotal(String id);

    void addCart(CartDTO cart);

    void addOrder(OrderDTO order);

    void addDetail(List<DetailDTO> detailList);

    void removeCart(List<Integer> idList);

    void updateStock(List<DetailDTO> detailList);

    void updateMileage(OrderDTO order);

    void loggingMileage(MileageDTO mileage);

    void removeCartProduct(int cart_id);

}
