package com.pjf.core.service.staticpage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */

public class StaticPageServiceImpl implements StaticPageService ,ServletContextAware{
    private Configuration config;

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.config = freeMarkerConfigurer.getConfiguration();
    }

    @Override
    public void index(Map<String, Object> root, Long id) {
        BufferedWriter out = null;
        try {
            Template template = config.getTemplate("productDetail.ftl");
            String path=getPath("/html/product/"+id+".html");
            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            template.process(root,out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }

    private String getPath(String name) {
        return servletContext.getRealPath(name);
    }
}
