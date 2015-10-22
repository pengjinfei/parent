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
public class CenterController {

    //入口
    @RequestMapping(value = "/index.do")
    public String index(Model model){


        return "index";
    }
    //头   Springmvc 相对路径
    @RequestMapping(value = "/top.do")
    public String top(Model model){


        return "top";
    }
    //身体
    @RequestMapping(value = "/main.do")
    public String main(Model model){


        return "main";
    }
    //左
    @RequestMapping(value = "/left.do")
    public String left(Model model){


        return "left";
    }
    //右
    @RequestMapping(value = "/right.do")
    public String right(Model model){


        return "right";
    }

/*    @Autowired
    private TestTbService testTbService;

    @RequestMapping(value = "/indextest.do")
    public String indexTest(Model model){

        //testTbService.insertTestTb(testTb);
        //Shift + alt + L
        List<TestTb> testTbs = testTbService.selectTestTbList();

        model.addAttribute("testTbs", testTbs);

        return "index";
    }*/


}
