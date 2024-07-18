package br.com.alura.screensound;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    RestTemplate restTemplate(ApplicationContext context) {
        return new RestTemplate();
    }

}
