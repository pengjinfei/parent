package com.pjf.core.controller;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.bean.product.Color;
import com.pjf.core.bean.product.Product;
import com.pjf.core.bean.product.Sku;
import com.pjf.core.service.product.ProductService;
import com.pjf.core.service.product.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by pengjinfei on 2015/10/23.
 * Description:
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;
    @RequestMapping("/display/list.shtml")
    public String list(Integer pageNo,String keyword,Long brandId,String price,Model model) throws Exception {
        List<Brand> brands = productService.selectBrandListFormRedis();
        model.addAttribute("brands", brands);

        Map<String,String> requestMap=new HashMap<>();
        if (brandId != null) {
            String name = productService.selectBrandNameById(brandId);
            requestMap.put("品牌", name);
        }
        if (StringUtils.isNotEmpty(price)) {
            requestMap.put("价格", price);
        }
        model.addAttribute("map", requestMap);
        Pagination pagination = productService.selectPaginationbyQueryFromSolr(pageNo, keyword,brandId,price);
        model.addAttribute("pagination", pagination);
        return "product/product";
    }

    @RequestMapping("/detail.shtml")
    public String detail(Long id, Model model) {
        Product product = productService.selectProdutById(id);
        model.addAttribute("product", product);

        List<Sku> skus = skuService.selectSkuListByProductIdWithStock(id);
        model.addAttribute("skus", skus);

        Set<Color> colors = new HashSet<>();
        for (Sku sku : skus) {
            colors.add(sku.getColor());
        }
        model.addAttribute("colors", colors);

        return "product/productDetail";
    }

}
