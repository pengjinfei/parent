package com.pjf.core.controller;

import com.pjf.core.service.product.UploadService;
import com.pjf.core.web.Constants;
import net.fckeditor.response.UploadResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("/uploadPic.do")
    public void uploadPic(@RequestParam(required = false) MultipartFile pic, HttpServletResponse response) throws Exception {
        String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("path", path);
        jsonObject.put("url", Constants.IMG_URL + path);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonObject.toString());
    }

    @RequestMapping("/uploadFck.do")
    public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws Exception {
        MultipartRequest multiRequest= (MultipartRequest) request;
        Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();
        for (Map.Entry<String, MultipartFile> entry : entries) {
            MultipartFile file = entry.getValue();
            String path = uploadService.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
            String url=Constants.IMG_URL+path;
            UploadResponse uploadResponse=UploadResponse.getOK(url);
            response.getWriter().print(uploadResponse);
        }
    }
}
