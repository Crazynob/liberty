package red.fuyun.liberty.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import red.fuyun.liberty.annotation.RestControllerResult;
import red.fuyun.liberty.pojo.User;
import red.fuyun.liberty.service.UserService;

@RestControllerResult
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public User user(String uname,String pwd){
        User user = userService.queryUser(uname, pwd);
        return null;
    }

    @PostMapping("/updatepwd")
    public User updatePwd(String pwd){
        return null;
    }





}
