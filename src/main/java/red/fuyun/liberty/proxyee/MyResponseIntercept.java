package red.fuyun.liberty.proxyee;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullResponseIntercept;
import com.github.monkeywie.proxyee.server.auth.BasicHttpProxyAuthenticationProvider;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;


@Component
public class MyResponseIntercept extends FullResponseIntercept {

    @Autowired
    private MapppingMthod mapppingMthod;

    @Override
    public boolean match(HttpRequest httpRequest, HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        String uri = httpRequest.uri();
        return mapppingMthod.container(uri);
    }

    @Override
    public void handleResponse(HttpRequest httpRequest, FullHttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
        System.out.println("进入handleResponse");
        try {
           process(httpRequest, httpResponse, pipeline);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean process(HttpRequest httpRequest, FullHttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) throws IOException {
        String uri = httpRequest.uri();
        MapppingMthod.CellWarpper cell = mapppingMthod.get(uri);
        if (Objects.nonNull(cell)){
            System.out.println(uri);
            boolean apply = cell.apply(httpRequest, httpResponse, pipeline);
            System.out.println(apply);
            return apply;
        }
        return false;
    }
}
