package com.pjf.common.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public class CustomTrimConverter implements Converter<String,String> {
    @Override
    public String convert(String source) {
        if (source != null) {
            source=source.trim();
            if(!"".equals(source))
                return source;
            else
                return null;
        }
        return null;
    }
}
