package com.ins1st.contants;

/**
 * jwt配置类
 * @author sun
 */
public interface JwtConstants {

    String AUTH_HEADER = "token";

    String SECRET = "defaultSecret";

    Long EXPIRATION = 604800L;

    String AUTH_PATH = "/api/login";
}
