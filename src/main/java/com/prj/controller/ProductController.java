package com.prj.controller;

import com.prj.dto.CartDTO;
import com.prj.dto.CategoryDTO;
import com.prj.dto.ProductDTO;
import com.prj.dto.StockDTO;
import com.prj.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @RequestMapping("uploadPage")
    public String uploadPage(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("category", new CategoryDTO());
        model.addAttribute("stock", new StockDTO());

        return "product/upload";
    }

    @RequestMapping("upload")
    public String upload(@ModelAttribute("product") ProductDTO product, @ModelAttribute("category") CategoryDTO category, @ModelAttribute("stock") StockDTO stock, @RequestParam("file") List<MultipartFile> files) throws IOException {
        if (!files.isEmpty()) {
            String path = "/home/ubuntu/uploadImage/";
            StringBuilder sb = new StringBuilder();

            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
                sb.append(fileName + ",");

                file.transferTo(new File(path + "/" + fileName));
            }
            product.setImage(sb.toString());
        }

        int quantity = stock.getQuantity();
        stock.setTotal(quantity * stock.getSize().size());

        if (!stock.getSize().isEmpty()) {
            for (String size : stock.getSize()) {
                if (size.equals("1"))
                    stock.setOne(quantity);
                else if (size.equals("2"))
                    stock.setTwo(quantity);
                else if (size.equals("3"))
                    stock.setThree(quantity);
                else if (size.equals("4"))
                    stock.setFour(quantity);
                else if (size.equals("5"))
                    stock.setFive(quantity);
                else
                    stock.setSix(quantity);
            }
        }

        product.setCategory_id(service.getCategoryId(category));
        stock.setProduct_id(service.upload(product));
        service.setStock(stock);

        return "redirect:/";
    }


    @RequestMapping("productList")
    public String productList(@RequestParam("page") int page, @RequestParam("category_id") int category_id, Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("category_id", category_id);

        List<ProductDTO> productList = service.getProductList(map);
        CategoryDTO category = service.getCategory(category_id);

        int listCount = service.getProductCount(category_id);
        int limit = 6;

        int pageCount = listCount / limit + ((listCount % limit == 0) ? 0 : 1);
        int startPage = ((page - 1) / 3) * limit + 1;
        int endPage = startPage + 3 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        model.addAttribute("page", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("listCount", listCount);

        model.addAttribute("productList", productList);
        model.addAttribute("parent", getParent(category.getParent_id()));
        model.addAttribute("category", category);

        return "product/list";
    }

    @RequestMapping(value = "productDetail", method = RequestMethod.GET)
    public String productDetail(@RequestParam("product_id") int product_id, Model model) {
        if (model.asMap().get("flag") != null)
            model.addAttribute("flag", model.asMap().get("flag"));

        ProductDTO product = service.getProduct(product_id);
        StockDTO stock = service.getStock(product_id);

        model.addAttribute("product", product);
        model.addAttribute("stock", stock);
        model.addAttribute("cart", new CartDTO());

        CategoryDTO category = service.getCategoryByProduct(product_id);
        model.addAttribute("parent", getParent(category.getParent_id()));
        model.addAttribute("category", category);

        return "product/detail";
    }

    @RequestMapping("search")
    public String search(@RequestParam("page") int page, @RequestParam("product_name") String product_name, Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("product_name", product_name);

        List<ProductDTO> searchList = service.getSearchList(map);

        int listCount = service.getSearchCount(product_name);
        int limit = 6;

        int pageCount = listCount / limit + ((listCount % limit == 0) ? 0 : 1);
        int startPage = ((page - 1) / 3) * limit + 1;
        int endPage = startPage + 3 - 1;

        if (endPage > pageCount)
            endPage = pageCount;

        model.addAttribute("page", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("listCount", listCount);

        model.addAttribute("searchList", searchList);
        model.addAttribute("product_name", product_name);

        return "product/search";
    }

    private String getParent(int parent_id) {
        String[] parent = {"Mens", "Womens", "Footwear", "Accessories"};
        for (int i = 1; i <= 4; i++) {
            if (parent_id == i)
                return parent[i - 1];
        }

        return null;
    }

}
