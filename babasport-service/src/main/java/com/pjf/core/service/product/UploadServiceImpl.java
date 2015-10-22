package com.pjf.core.service.product;

import com.pjf.common.fastdfs.FastDFSUtils;
import org.springframework.stereotype.Service;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {
    @Override
    public String uploadPic(byte[] pic, String name, Long size) throws Exception {
        return FastDFSUtils.uploadPic(pic, name, size);
    }
}
