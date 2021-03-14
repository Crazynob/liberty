package red.fuyun.liberty.configuration;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptInitializer;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.server.HttpProxyServer;
import com.github.monkeywie.proxyee.server.HttpProxyServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import red.fuyun.liberty.proxyee.Auth;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProxyeeConfig {

    @Bean
    public HttpProxyServerConfig serverConfig(Auth auth){
        HttpProxyServerConfig config = new HttpProxyServerConfig();
        config.setHandleSsl(true);
        config.setAuthenticationProvider(auth);
        return config;
    }


    @Bean
    public HttpProxyServer httpProxyServer( List<HttpProxyIntercept> intercepts,HttpProxyServerConfig serverConfig){
        System.out.println(intercepts.size());
        HttpProxyServer httpProxyServer = new HttpProxyServer()
                .serverConfig(serverConfig)
                .proxyInterceptInitializer(new HttpProxyInterceptInitializer() {
                    @Override
                    public void init(HttpProxyInterceptPipeline pipeline) {
                        intercepts.forEach(intercept -> {
                            pipeline.addLast(intercept);
                        });

                    }
                });

        return httpProxyServer;
    }

}
