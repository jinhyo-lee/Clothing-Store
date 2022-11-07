package com.prj.controller;

import com.prj.dto.CartDTO;
import com.prj.dto.DetailDTO;
import com.prj.dto.MemberDTO;
import com.prj.dto.OrderDTO;
import com.prj.service.MemberService;
import com.prj.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @RequestMapping("cart")
    public String cart(Authentication authentication, Model model) {
        if (model.asMap().get("flag") != null)
            model.addAttribute("flag", model.asMap().get("flag"));

        String id = authentication.getName();

        List<CartDTO> cartList = orderService.getCart(id);
        Integer total = orderService.getTotal(id);

        if (total == null)
            total = 0;

        model.addAttribute("cartList", cartList);
        model.addAttribute("total", total);

        return "order/cart";
    }

    @RequestMapping("addCart")
    public String addCart(@ModelAttribute CartDTO cart, Authentication authentication, RedirectAttributes attributes) {
        cart.setId(authentication.getName());
        cart.setPrice(cart.getPrice() * cart.getQuantity());

        orderService.addCart(cart);
        attributes.addFlashAttribute("flag", true);

        return "redirect:productDetail?product_id=" + cart.getProduct_id();
    }

    @RequestMapping("orderPage")
    public String orderPage(Authentication authentication, RedirectAttributes attributes, Model model) {
        String id = authentication.getName();

        MemberDTO member = memberService.getMember(id);
        List<CartDTO> cartList = orderService.getCart(id);
        if (cartList.isEmpty()) {
            attributes.addFlashAttribute("flag", true);
            return "redirect:cart";
        }
        Integer total = orderService.getTotal(id);

        model.addAttribute("member", member);
        model.addAttribute("cartList", cartList);
        model.addAttribute("total", total);
        model.addAttribute("detail", new DetailDTO());

        return "order/order";
    }

    @RequestMapping("order")
    public String order(@ModelAttribute MemberDTO member, @ModelAttribute DetailDTO detail, @ModelAttribute OrderDTO order, @RequestParam("addressDetail") String addressDetail, @RequestParam("paymentMethod") String paymentMethod, Authentication authentication, Model model) {
        String id = authentication.getName();

        order.setId(id);
        order.setAddress(member.getName() + "," + member.getAddress() + "," + addressDetail + "," + member.getContact());
        if (paymentMethod.equals("credit"))
            order.setPayment("신용 / 체크카드");
        else
            order.setPayment(order.getBankName() + "," + order.getAccountName() + "," + order.getAccountNumber());

        List<CartDTO> cartList = orderService.getCart(id);

        orderService.checkOut(order, cartList);

        return "redirect:dashboard?orderPage=1&mileagePage=1";
    }

    @RequestMapping("removeCartProduct")
    public String removeCartProduct(@RequestParam("cart_id") int cart_id) {
        orderService.removeCartProduct(cart_id);

        return "redirect:cart";
    }

}
