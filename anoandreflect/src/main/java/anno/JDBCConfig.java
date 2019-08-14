package anno;

import java.lang.annotation.*;

/**
 * 作用域：方法 类
 * 生命周期： 运行时
 * 可被继承： 是
 * 文档化： 是
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JDBCConfig {
    String ip();
    int port() default 3306;
    String database();
    String encoding();
    String loginName();
    String password();
    String serverTimezone() default "Asia/Shanghai";
}
