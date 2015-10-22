package com.pjf.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pengjinfei on 2015/10/17.
 * Description:
 */
@Controller
@RequestMapping(value = "/control")
public class FrameController {


    //商品身体
    @RequestMapping(value = "/frame/product_main.do")
    public String product_main(Model model) {


        return "frame/product_main";
    }

    //商品身体--左
    @RequestMapping(value = "/frame/product_left.do")
    public String product_left(Model model) {


        return "frame/product_left";
    }
}
