package red.fuyun.liberty.pojo;

import red.fuyun.liberty.enums.ResultStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class ResultVO<T>{
    private Integer code;
    private String message;
    private T data;

    ResultVO(ResultStatus status, T data){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public static ResultVO<Void> success(){
        return new ResultVO<Void>(ResultStatus.SUCCESS,null);
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<T>(ResultStatus.SUCCESS,data);
    }

    public static <T> ResultVO<T> success(ResultStatus status, T data){
        if (Objects.isNull(status)){
            return success(data);
        }
        return new ResultVO<T>(status,data);
    }



    public static <T> ResultVO<T> failure(){
        return new ResultVO<T>(ResultStatus.ERROR,null);
    }

    public static <T> ResultVO<T> failure(T data){
        return new ResultVO<T>(ResultStatus.ERROR,data);
    }

    public static <T> ResultVO<T> failure(ResultStatus status, T data){
        if (Objects.isNull(status)){
            return success(data);
        }
        return new ResultVO<T>(status,data);
    }
    

}
