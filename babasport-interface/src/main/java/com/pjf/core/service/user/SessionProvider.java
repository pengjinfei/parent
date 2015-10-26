package com.pjf.core.service.user;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
public interface SessionProvider {
    void setUserName(String sessionId, String name);

    String getUserName(String sessionId);

    void setValidateCode(String sessionId, String code);

    String getValidateCode(String sessionId);
}
