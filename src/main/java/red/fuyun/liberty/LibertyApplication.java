package red.fuyun.liberty;

import com.github.monkeywie.proxyee.server.HttpProxyServer;
import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
@EnableCaching
@MapperScan(basePackages = {"red.fuyun.liberty.mapper"})
public class LibertyApplication {

    @Autowired
    private HttpProxyServer httpProxyServer;

    @PostConstruct
     private void init(){
        System.out.println("服务启动成功...........");
        httpProxyServer.start(6666);
    }

    public static void main(String[] args) {
        SpringApplication.run(LibertyApplication.class, args);
    }

}

