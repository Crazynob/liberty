package red.fuyun.liberty.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@RestController
public @interface RestControllerResult {
}
