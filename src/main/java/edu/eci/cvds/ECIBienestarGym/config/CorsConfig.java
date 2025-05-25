package edu.eci.cvds.ECIBienestarGym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Cambié a /** para que acepte en todas las rutas
                        .allowedOriginPatterns("*")  // Permite cualquier origen
                        .allowedMethods("*")  // Permite cualquier método HTTP
                        .allowedHeaders("*")  // Permite cualquier header
                        .allowCredentials(true); // Permite enviar cookies y auth headers, opcional según necesidad
            }
        };
    }
}
