package red.fuyun.liberty.pojo;


import lombok.Data;


import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
public class User {

    private Integer id;

    @NotNull(message = "账号不能为空")
    private String uname;

    @NotNull(message = "密码不能为空")
    private String pwd;


    private Timestamp createTime;

    private Timestamp lastTime;

    private String studentNo;

}
