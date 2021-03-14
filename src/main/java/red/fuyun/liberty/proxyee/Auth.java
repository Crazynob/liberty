package red.fuyun.liberty.proxyee;

import com.github.monkeywie.proxyee.server.auth.BasicHttpProxyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import red.fuyun.liberty.pojo.User;
import red.fuyun.liberty.service.UserService;

import java.util.Objects;

@Component
public class Auth extends BasicHttpProxyAuthenticationProvider {

    @Autowired
    UserService userService;

    private Integer count  = 0;
    @Override
    protected boolean authenticate(String uname, String pwd) {


        return true;
    }

    @Cacheable
    public User queryUser(String uname,String pwd) {
        System.out.println("执行方法了----------");
        User user = userService.queryUser(uname, pwd);
        return user;
    }
}
