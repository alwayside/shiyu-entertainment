package com.shiyu.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthorityCenter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<Long, TokenCache> tokenCacheCenter = new ConcurrentHashMap<>();

    private final String USER_HEADER_KEY = "User";

    private final String TOKEN_HEADER_KEY = "Authorization";

    private final String USER_ID_HEADER_KEY = "UserId";

    public AuthorityCenter() {
        logger.info("System: AuthorityCenter is setting");
    }

    //登录
    public String userLogin(Long user, String token) {
        if (user == null || token == null) {
            return "login failed";
        }
        if (!tokenCacheCenter.containsKey(user)) {
            TokenCache tokenCache = createTokenCache(token);
            tokenCacheCenter.putIfAbsent(user, tokenCache);
        }
        // 如果已经登录就更新token值
        else {
            tokenCacheCenter.remove(user);
            TokenCache tokenCache = createTokenCache(token);
            tokenCacheCenter.putIfAbsent(user, tokenCache);
        }
        logger.info("System: User: {} , has been logged,  time {}", user, new Date().toString());
        return "login success";
    }

    //登出
    public String userLogout(String user) {
        tokenCacheCenter.remove(user);
        logger.info("System: User: {} , has been log out,  time {}", user, new Date().toString());
        return "logout success";
    }

    //确认登录状态
    public void check(HttpServletRequest request) throws Throwable {
        String user = request.getHeader(USER_ID_HEADER_KEY);
        String token = request.getHeader(TOKEN_HEADER_KEY);
        if (checkToken(user, token)) {
            return;
        }
        throw new Throwable("User Not loggin");
    }

    //确认登录状态
    public boolean checkToken(String user, String token) {
        if (user == null || token == null) {
            return false;
        }
        TokenCache tokenCache = tokenCacheCenter.get(Long.valueOf(user));
        if (tokenCache.getUserToken().equals(token)) {
            if (tokenCache.getExpiryTime() < System.currentTimeMillis()) {
                tokenCacheCenter.remove(user);
                return false;
            }
            //验证成功 更新过期时间
            tokenCache.setExpiryTime(getGenerateExpiryTime());
            return true;
        }
        return false;
    }

    //创建新的tokenCache
    private TokenCache createTokenCache(String token) {
        TokenCache tokenCache = new TokenCache();
        tokenCache.setUserToken(token);
        tokenCache.setExpiryTime(getGenerateExpiryTime());
        return tokenCache;
    }

    //生成过期时间
    private Long getGenerateExpiryTime() {
        Long curTime = System.currentTimeMillis();
        curTime = curTime + 1000 * 60 * 10L;
        return curTime;
    }

    public Long getUserId(HttpServletRequest request) {
        Long userId = null;
        try {
            userId = Long.valueOf(request.getHeader(USER_ID_HEADER_KEY));
        } catch (Exception e) {
            return null;
        }
        return userId;
    }


    protected static class TokenCache {

        String userToken;

        Long expiryTime;

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public Long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(Long expiryTime) {
            this.expiryTime = expiryTime;
        }
    }
}
