package com.pjf.core.service.user;

import com.pjf.core.web.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
public class CacheSessionProvider implements SessionProvider {

    @Autowired
    private Jedis jedis;
    private int exp=30;
    private int codeExp=1;

    public void setCodeExp(int codeExp) {
        this.codeExp = codeExp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public void setUserName(String sessionId, String name) {
        jedis.set(sessionId + ":" + Constants.USER_NAME, name);
        jedis.expire(sessionId + ":" + Constants.USER_NAME, 60 * exp);
    }

    @Override
    public String getUserName(String sessionId) {
        String username = jedis.get(sessionId + ":" + Constants.USER_NAME);
        if (username != null) {
            jedis.expire(sessionId + ":" + Constants.USER_NAME, 60 * exp);
        }
        return username;
    }
    @Override
    public void setValidateCode(String sessionId, String code) {
        jedis.set(sessionId + ":" + Constants.VALIDATECODE, code);
        jedis.expire(sessionId + ":" + Constants.VALIDATECODE, 60 * codeExp);
    }
    @Override
    public String getValidateCode(String sessionId) {
        String code = jedis.get(sessionId + ":" + Constants.VALIDATECODE);
        if (code != null) {
            jedis.del(sessionId + ":" + Constants.VALIDATECODE);
        }
        return code;
    }
}
