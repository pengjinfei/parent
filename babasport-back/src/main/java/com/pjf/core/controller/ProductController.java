package com.pjf.core.controller;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.bean.product.Color;
import com.pjf.core.bean.product.Product;
import com.pjf.core.bean.product.Type;
import com.pjf.core.service.product.BrandService;
import com.pjf.core.service.product.ColorService;
import com.pjf.core.service.product.ProductService;
import com.pjf.core.service.product.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by pengjinfei on 2015/10/20.
 * Description:
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ColorService colorService;
    @RequestMapping("/list.do")
    public String list(Integer pageNo,String name,Long brandId,Boolean isShow,Model model) throws Exception {
        List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
        model.addAttribute("brands", brands);
        Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow);
        model.addAttribute("pagination", pagination);
        model.addAttribute("name", name);
        model.addAttribute("brandId", brandId);
        model.addAttribute("isShow", isShow);
        return "product/list";
    }

    @RequestMapping("/toAdd.do")
    public String toAdd(Model model) {
        List<Type> types = typeService.selectTypeList();
        model.addAttribute("types", types);
        List<Brand> brands = brandService.selectBrandListByQuery(null, 1);
        model.addAttribute("brands", brands);
        List<Color> colors = colorService.selectColorList();
        model.addAttribute("colors", colors);
        return "product/add";
    }

    @RequestMapping("/add.do")
    public String add(Product product) {
        productService.insert(product);
        return "redirect:/product/list.do";
    }

    //上架
    @RequestMapping(value = "/isShow.do")
    public String isShow(Long[] ids){
        //上架 Service
        productService.isShow(ids);

        return "redirect:/product/list.do";
    }
}
