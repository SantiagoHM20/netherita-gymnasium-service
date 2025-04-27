package edu.eci.cvds.ECIBienestarGym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("ECIBienestar-Gymnasium API")
                        .version("1.0")
                        .description("Documentación de la API del sistema de gimnasio De la aplicación ECIBienestar en la Escuela Colombiana de Ingeniería."));
    }
}