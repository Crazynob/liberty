package red.fuyun.liberty.advice;

import red.fuyun.liberty.pojo.ResultVO;
import red.fuyun.liberty.util.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.exmple.helper.controller"})
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
//        boolean hasAnnotation = AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResponseResult.class);
//        boolean hasMethodAnnotation = methodParameter.hasMethodAnnotation(ResponseResult.class);
        return !returnType.getGenericParameterType().equals(ResultVO.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {


        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString( ResultVO.success(body));
            } catch (JsonProcessingException e) {
                throw new ApiException("返回String类型错误");
            }
        }
        // 将原本的数据包装在ResultVO里
        return ResultVO.success(body);
    }
}





