package red.fuyun.liberty.advice;

import red.fuyun.liberty.pojo.ResultVO;
import red.fuyun.liberty.util.ApiException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String defaultMessage = objectError.getDefaultMessage();

        // 然后提取错误提示信息进行返回
        return ResultVO.failure(defaultMessage);
    }

    @ExceptionHandler(ApiException.class)
    public ResultVO APIExceptionHandler(ApiException e) {
        return ResultVO.failure(e.getMsg());
    }



    @ExceptionHandler(Exception.class)
    public ResultVO ExceptionHandler(Exception e) {
        return ResultVO.failure(e.getMessage());
    }


}
