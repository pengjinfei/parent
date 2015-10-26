package com.pjf.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
public class RequestUtils {

    public static String getCSESSIONID(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("CSESSIONID")) {
                return cookie.getValue();
            }
        }
        String CSESSIONID = UUID.randomUUID().toString().replace("-", "");
        Cookie cookie = new Cookie("CSESSIONID", CSESSIONID);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return  CSESSIONID;
    }
}
