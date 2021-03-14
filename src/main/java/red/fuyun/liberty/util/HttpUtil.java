package red.fuyun.liberty.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class HttpUtil {

    @Autowired
    OkHttpClient okHttpClient;


    public JSONObject postJson(String url,JSONObject body, Map<String,String> headers) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body.toString());

        Request request = new Request.Builder().
                url(url)
                .post(requestBody)
                .headers(Headers.of(headers))
                .build();

        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
        String respBody = execute.body().string();
        execute.close();
        return JSONObject.parseObject(respBody);
    }

}
