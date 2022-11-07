package com.prj.controller;

import com.prj.dto.DetailDTO;
import com.prj.dto.MemberDTO;
import com.prj.dto.MileageDTO;
import com.prj.dto.OrderDTO;
import com.prj.service.DashboardService;
import com.prj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final MemberService memberService;

    @RequestMapping("dashboard")
    public String dashboard(@RequestParam("orderPage") int orderPage, @RequestParam("mileagePage") int mileagePage, Authentication authentication, Model model) {
        if (model.asMap().get("flag") != null)
            model.addAttribute("flag", model.asMap().get("flag"));

        String id = authentication.getName();

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", orderPage);
        List<OrderDTO> orderList = dashboardService.getOrderList(map);

        int orderCount = dashboardService.getOrderCount(id);
        setPageElements(orderPage, orderCount, "Order", model);

        map.put("page", mileagePage);
        List<MileageDTO> mileageList = dashboardService.getMileageList(map);

        int mileageCount = dashboardService.getMileageCount(id);
        setPageElements(mileagePage, mileageCount, "Mileage", model);

        MemberDTO member = memberService.getMember(id);

        model.addAttribute("orderList", orderList);
        model.addAttribute("mileageList", mileageList);
        model.addAttribute("member", member);

        return "dashboard/dashboard";
    }

    @RequestMapping("orderInfo")
    public String orderInfo(@RequestParam("order_id") int order_id, Authentication authentication, Model model) {
        String id = authentication.getName();

        List<DetailDTO> detailList = dashboardService.getDetailList(order_id);
        OrderDTO order = dashboardService.getOrder(order_id);
        String address = order.getAddress();
        MemberDTO member = memberService.getMember(id);

        model.addAttribute("detailList", detailList);
        model.addAttribute("order", order);
        model.addAttribute("address", address);
        model.addAttribute("member", member);

        return "dashboard/order-info";
    }

    private void setPageElements(int page, int listCount, String flag, Model model) {
        int limit = 3;

        int pageCount = listCount / limit + ((listCount % limit == 0) ? 0 : 1);
        int startPage = ((page - 1) / 3) * limit + 1;
        int endPage = startPage + 3 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        model.addAttribute("page" + flag, page);
        model.addAttribute("startPage" + flag, startPage);
        model.addAttribute("endPage" + flag, endPage);
        model.addAttribute("pageCount" + flag, pageCount);
        model.addAttribute("listCount" + flag, listCount);
    }

    @RequestMapping("updateMember")
    public String updateMember(@ModelAttribute MemberDTO member, @RequestParam("addressDetail") String addressDetail, Authentication authentication, RedirectAttributes attributes) {
        member.setId(authentication.getName());
        member.setAddress(member.getAddress() + "," + addressDetail);

        dashboardService.updateMember(member);
        attributes.addFlashAttribute("flag", true);

        return "redirect:dashboard?orderPage=1&mileagePage=1";
    }

    @RequestMapping("changePasswordPage")
    public String changePasswordPage() {
        return "dashboard/change-password";
    }

    @ResponseBody
    @RequestMapping("checkPassword")
    public int checkPassword(String password, Authentication authentication) {
        return dashboardService.checkPassword(authentication.getName(), password);
    }

    @RequestMapping("changePassword")
    public String changePassword(@RequestParam("password") String password, Authentication authentication) {
        MemberDTO member = new MemberDTO();
        member.setId(authentication.getName());
        member.setPassword(password);

        dashboardService.changePassword(member);

        return "redirect:/";
    }

    @RequestMapping("quitPage")
    public String quitPage() {
        return "dashboard/quit";
    }

    @RequestMapping("quit")
    public String quit(Authentication authentication) {
        dashboardService.quit(authentication.getName());

        return "redirect:logout";
    }

}
