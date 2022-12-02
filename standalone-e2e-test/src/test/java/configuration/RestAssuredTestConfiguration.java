package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("config")
//${spring.profiles.active:dev} -> use spring.profiles.active or the default "dev"
@PropertySource("classpath:/application-${spring.profiles.active:dev}.properties")
public class RestAssuredTestConfiguration {
}
