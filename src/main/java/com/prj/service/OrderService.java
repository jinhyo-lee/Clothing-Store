package com.prj.service;

import com.prj.dto.CartDTO;
import com.prj.dto.DetailDTO;
import com.prj.dto.MileageDTO;
import com.prj.dto.OrderDTO;
import com.prj.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper mapper;

    public List<CartDTO> getCart(String id) {
        return mapper.getCart(id);
    }

    public Integer getTotal(String id) {
        return mapper.getTotal(id);
    }

    public void addCart(CartDTO cart) {
        mapper.addCart(cart);
    }

    public void checkOut(OrderDTO order, List<CartDTO> cartList) {
        mapper.addOrder(order);

        List<DetailDTO> detailList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();

        for (int i = 0; i < cartList.size(); i++) {
            DetailDTO detail = new DetailDTO();
            detail.setOrder_id(order.getOrder_id());
            detail.setProduct_id(cartList.get(i).getProduct_id());
            detail.setSize(cartList.get(i).getSize());
            detail.setQuantity(cartList.get(i).getQuantity());
            detail.setPrice(cartList.get(i).getPrice());

            detailList.add(detail);
            idList.add(cartList.get(i).getCart_id());
        }

        mapper.addDetail(detailList);
        mapper.removeCart(idList);
        mapper.updateStock(detailList);
        mapper.updateMileage(order);

        MileageDTO mileage = new MileageDTO();
        mileage.setId(order.getId());
        mileage.setType("적립");
        mileage.setAmount((int) (order.getCost() * 0.03));
        mapper.loggingMileage(mileage);

        if (order.getDiscount() > 0) {
            mileage.setType("차감");
            mileage.setAmount(-order.getDiscount());
            mapper.loggingMileage(mileage);
        }
    }

    public void removeCartProduct(int cart_id){
        mapper.removeCartProduct(cart_id);
    }

}
