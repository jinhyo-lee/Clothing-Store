package com.prj.controller;

import com.prj.dto.CategoryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("mainPage")
    public String mainPage(Model model) {
        CategoryDTO category = new CategoryDTO();
        model.addAttribute("category", category);

        return "main";
    }

}
