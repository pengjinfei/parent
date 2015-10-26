package com.pjf.core.controller;

import com.pjf.common.utils.RequestUtils;
import com.pjf.core.bean.user.Buyer;
import com.pjf.core.service.user.BuyerService;
import com.pjf.core.service.user.SessionProvider;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
@Controller
public class LoginController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SessionProvider sessionProvider;

    @RequestMapping(value = "/shopping/login.aspx", method = RequestMethod.GET)
    public String login() {
        return "buyer/login";
    }

    @RequestMapping(value = "/shopping/login.aspx", method = RequestMethod.POST)
    public String login(String username, String password, String code, String returnUrl
            , HttpServletRequest request, HttpServletResponse response, Model model) {
        if (code == null) {
            model.addAttribute("error", "验证码不能为空");
            return "buyer/login";
        }
        String csessionid = RequestUtils.getCSESSIONID(request, response);
        String correctCode = sessionProvider.getValidateCode(csessionid);
        if (!correctCode.equalsIgnoreCase(code)) {
            model.addAttribute("error", "验证码错误");
            return "buyer/login";
        }
        if (username == null) {
            model.addAttribute("error", "用户名不能为空");
            return "buyer/login";
        }
        Buyer buyer = buyerService.selectByUsername(username);
        if (buyer == null) {
            model.addAttribute("error", "用户名不正确");
            return "buyer/login";
        }
        if (password == null) {
            model.addAttribute("error", "密码不能为空");
            return "buyer/login";
        }
        if (!encodePassword(password).equals(buyer.getPassword())) {
            model.addAttribute("error", "密码不正确");
            return "buyer/login";
        }
        sessionProvider.setUserName(csessionid,username);
        return "redirect:"+returnUrl;
    }

    private String encodePassword(String password) {
        char[] encodeHex = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            encodeHex = Hex.encodeHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(encodeHex);
    }

    @RequestMapping(value = "/shopping/getCodeImage.aspx")
    public void getCodeImage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("#######################生成数字和字母的验证码#######################");
        BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
        // 得到该图片的绘图对象
        Graphics g = img.getGraphics();

        Random r = new Random();

        Color c = new Color(200, 150, 255);

        g.setColor(c);

        // 填充整个图片的颜色

        g.fillRect(0, 0, 68, 22);

        // 向图片中输出数字和字母

        StringBuffer sb = new StringBuffer();

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        int index, len = ch.length;

        for (int i = 0; i < 4; i++) {

            index = r.nextInt(len);

            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

                    (255)));

            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
            // 输出的 字体和大小

            g.drawString("" + ch[index], (i * 15) + 3, 18);
            // 写什么数字，在图片 的什么位置画

            sb.append(ch[index]);

        }
        // 把上面生成的验证码放到Session域中
//        sessionProvider.setAttributeForCode(RequestUtils.getCSESSIONID(response, request), sb.toString());
        sessionProvider.setValidateCode(RequestUtils.getCSESSIONID(request, response), sb.toString());
        try {
            ImageIO.write(img, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
