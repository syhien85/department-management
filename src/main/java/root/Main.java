package root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import root.interceptor.LogInterceptor;

@SpringBootApplication
@EnableJpaAuditing
public class Main implements WebMvcConfigurer {

    public static void main(String[] args) {
        //
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logInterceptor);
    }
}
