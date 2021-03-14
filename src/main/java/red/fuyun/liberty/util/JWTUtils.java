package red.fuyun.liberty.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;


public class JWTUtils {
    //密钥
    private static String SECRET = "@$&$0@@A";
    //token有效期
    private static int TIME_OUT = 1;
    /**
     * 生产token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, TIME_OUT); //默认1小时过期

        builder.withExpiresAt(instance.getTime());//指定令牌的过期时间
        String token = builder.sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证token
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
