package red.fuyun.liberty.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.servlet.HandlerInterceptor;
import red.fuyun.liberty.util.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return true;
    }
}
