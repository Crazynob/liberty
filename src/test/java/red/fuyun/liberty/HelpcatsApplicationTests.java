package red.fuyun.liberty;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import red.fuyun.liberty.mapper.UserMapper;
import red.fuyun.liberty.pojo.User;

import java.util.List;

@SpringBootTest
class HelpcatsApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);

        userList.forEach(System.out::println);
    }

}
