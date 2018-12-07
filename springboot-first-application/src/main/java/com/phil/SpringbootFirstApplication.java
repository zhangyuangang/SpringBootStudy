package com.phil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
//@EnableCaching   开启缓存，还需要在service上 @Cacheable("account")
//@EnableScheduling   开启定时任务，运行 @Scheduled 方法
public class SpringbootFirstApplication {

    private static final Log log = LogFactory.getLog(SpringbootFirstApplication.class);

    public static void main(String[] args) {
        String s = new String();
        SpringApplication.run(SpringbootFirstApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
//            String quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
//            log.info(quote.toString());
        };
    }

}
