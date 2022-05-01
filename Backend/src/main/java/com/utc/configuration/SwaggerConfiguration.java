package com.utc.configuration;

import org.hibernate.mapping.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandlerKey;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(UTCApiInfo());
    }


    private ApiInfo UTCApiInfo(){
        return new ApiInfo("Booking Hotel API","","1.0","",
                new Contact("UTC",
                        "https://github.com/CaRot1911/UTCWebsiteBookingHotel.git",
                        "conga2292001@gmail.com"),"",""
                , Collections.emptyList());
    }
}