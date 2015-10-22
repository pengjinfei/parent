package com.pjf.core.controller;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.service.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping("/list.do")
    public String list(Integer pageNo,String name,Integer isDisplay,Model model) {
        if (pageNo == null) {
            pageNo=1;
        }
        Pagination pagination = brandService.selectPaginationByQuery(pageNo, name, isDisplay);
        model.addAttribute("pagination", pagination);
        model.addAttribute("name", name);
        model.addAttribute("isDisplay", isDisplay);
        return "brand/list";
    }

    @RequestMapping("/toAdd.do")
    public String toAdd() {
        return "brand/add";
    }

    @RequestMapping("/add.do")
    public String add(Brand brand) {
        brandService.insertBrand(brand);
        return "redirect:/brand/list.do";
    }

    @RequestMapping("/deleteBatch.do")
    public String deleteBatch(Long[] ids,String name,Integer isDisplay,Integer pageNo,Model model) {
        brandService.deleteBatch(ids);
        if (null != name) {
            model.addAttribute("name", name);
        }
        if (null != isDisplay) {
            model.addAttribute("isDisplay", isDisplay);
        }
        if (null != pageNo) {
            model.addAttribute("pageNo", pageNo);
        }
        return "redirect:/brand/list.do";
    }

    @RequestMapping("/deleteById.do")
    public String deleteById(Long id,String name,Integer isDisplay,Integer pageNo,Model model) {
        brandService.deleteById(id);
        if (null != name) {
            model.addAttribute("name", name);
        }
        if (null != isDisplay) {
            model.addAttribute("isDisplay", isDisplay);
        }
        if (null != pageNo) {
            model.addAttribute("pageNo", pageNo);
        }
        return "redirect:/brand/list.do";
    }

    @RequestMapping("/toEdit.do")
    public String toEdit(Long id,Model model) {
        Brand brand= brandService.selectBrandById(id);
        model.addAttribute("brand", brand);
        return "brand/edit";
    }

    @RequestMapping("/edit.do")
    public String edit(Brand brand) {
        brandService.updateBrand(brand);
        return "redirect:/brand/list.do";
    }
}
