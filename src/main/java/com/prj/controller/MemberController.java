package com.prj.controller;

import com.prj.dto.MemberDTO;
import com.prj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("loginPage")
    public String loginPage(Model model) {
        model.addAttribute("input", new MemberDTO());

        return "member/login";
    }

    @GetMapping("loginError")
    public String loginError(@RequestParam("error") String error, @RequestParam("errorMessage") String errorMessage, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);

        return "member/login";
    }

    @RequestMapping("joinPage")
    public String joinPage(Model model) {
        model.addAttribute("input", new MemberDTO());

        return "member/join";
    }

    @RequestMapping("join")
    public String join(@ModelAttribute MemberDTO input, @RequestParam("addressDetail") String addressDetail) {
        input.setPassword(passwordEncoder.encode(input.getPassword()));
        input.setAddress(input.getAddress() + "," + addressDetail);

        service.join(input);

        return "main";
    }

    @ResponseBody
    @RequestMapping("idCheck")
    public int idCheck(String id) {
        return service.idCheck(id);
    }

}
