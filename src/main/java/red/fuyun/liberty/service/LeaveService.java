package red.fuyun.liberty.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.fuyun.liberty.mapper.LeaveMapper;
import red.fuyun.liberty.pojo.Leave;

@Service
public class LeaveService {

    @Autowired
    LeaveMapper leaveMapper;

    public Leave queryByUId(Integer uid){
        QueryWrapper<Leave> leaveQueryWrapper = new QueryWrapper<>();
        leaveQueryWrapper.eq("uid",uid);
        Leave leave = leaveMapper.selectOne(leaveQueryWrapper);
        return leave;
    }

}
