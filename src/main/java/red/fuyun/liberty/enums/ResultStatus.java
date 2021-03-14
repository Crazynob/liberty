package red.fuyun.liberty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum ResultStatus {

    SUCCESS(200,"OK"),
    ERROR(400,"error");
    private Integer code;
    private String message;

}
