package red.fuyun.liberty.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.fuyun.liberty.mapper.UserMapper;
import red.fuyun.liberty.pojo.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUser(String uname,String pwd){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("uname",uname);
        userQueryWrapper.eq("pwd",pwd);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }

    public User queryStudentNo(String studentNo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("student_no",studentNo);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


}
