package red.fuyun.liberty.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("`leave`")
public class Leave {
    private Integer id;
    private Integer uid;
    private Timestamp startTime;
    private Timestamp endTime;

    private  String leaveReason;

    private  String urgencyMobile;

}
