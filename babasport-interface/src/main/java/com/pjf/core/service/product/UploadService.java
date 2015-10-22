package com.pjf.core.service.product;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public interface UploadService {
    String uploadPic(byte[] pic, String name, Long size) throws Exception;
}
