package red.fuyun.liberty.proxyee;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullRequestIntercept;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import org.springframework.stereotype.Component;

//@Component
public class MyRequestIntercept extends FullRequestIntercept {
    @Override
    public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        String uri = httpRequest.uri();
        return false;
    }

    @Override
    public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        System.out.println("httpRequest");
    }
}
