package com.pjf.core.controller;

import com.pjf.core.bean.product.Sku;
import com.pjf.core.service.product.SkuService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pengjinfei on 2015/10/21.
 * Description:
 */
@Controller
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("/list.do")
    public String list(Long productId,Model model) {
        List<Sku> skus = skuService.selectByProductId(productId);
        model.addAttribute("skus", skus);
        return "sku/list";
    }

    @RequestMapping("/update.do")
    public void update(Sku sku, HttpServletResponse response) throws IOException {
        skuService.update(sku);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("message", "保存成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
